use std::cmp::max;

pub struct ArrayStack {
    backing_array: Vec<u64>,
    pub n: usize,
}

impl ArrayStack {
    pub fn new() -> ArrayStack {
        ArrayStack {
            backing_array: Vec::new(),
            n: 0,
        }
    }

    pub fn size(&self) -> usize {
        self.n
    }

    pub fn get(&self, i: usize) -> u64 {
        self.backing_array[i]
    }

    pub fn set(&mut self, i: usize, x: u64) -> u64 {
        let y = self.backing_array[i];
        self.backing_array[i] = x;
        y
    }

    pub fn add(&mut self, i: usize, x: u64) {
        // if self.n + 1 > self.backing_array.len() {
        //     self.resize()
        // }
        // for j in self.n..i {
        //     self.backing_array[j] = self.backing_array[j - 1];
        // }
        // self.backing_array[i] = x;
        // self.n += 1;
        self.add_all(i, &[x]);
    }

    pub fn remove(&mut self, i: usize) -> u64 {
        let x = self.backing_array[i];
        for j in i..(self.n - 1) {
            self.backing_array[j] = self.backing_array[j + 1];
        }
        self.n -= 1;
        if self.backing_array.len() >= 3 * self.n {
            self.resize();
        }
        x
    }

    pub fn add_all(&mut self, i: usize, c: &[u64]) {
        let mut next_array = vec![];
        next_array.resize(max((self.n + c.len()) * 2, 1), 0);

        let c_len = c.len();

        next_array[..i].copy_from_slice(&self.backing_array[..i]);
        next_array[i..(i + c_len)].copy_from_slice(c);
        next_array[(i + c_len)..(self.n + c_len)].copy_from_slice(&self.backing_array[i..self.n]);

        self.backing_array = next_array;
        self.n += c.len();
    }

    fn resize(&mut self) {
        let mut next_array = vec![];
        next_array.resize(max(self.n * 2, 1), 0);
        next_array[..self.n].copy_from_slice(&self.backing_array[..self.n]);
        // for i in 0..self.n {
        //     next_array[i] = self.backing_array[i];
        // }
        self.backing_array = next_array;
    }
}

#[cfg(test)]
mod tests {
    use crate::array::array_stack::ArrayStack;

    #[test]
    fn success_initialize() {
        let stack = ArrayStack::new();
        assert_eq!(0, stack.size());
        assert_eq!(0, stack.n);
        assert!(stack.backing_array.is_empty());
    }

    #[test]
    fn store_values() {
        let mut stack = ArrayStack::new();
        for i in 0..10 {
            stack.add(i, i as u64);
        }
        for i in 0..10 {
            assert_eq!(stack.remove(0), i);
        }
        assert_eq!(stack.size(), 0);
    }

    #[test]
    fn set_value() {
        let mut stack = ArrayStack::new();
        for i in 0..10 {
            stack.add(i, 100);
            stack.set(i, 0);
            assert_eq!(0, stack.get(i));
        }
    }

    #[test]
    fn add_all_values() {
        let mut stack = ArrayStack::new();
        for i in 0..10 {
            stack.add(i, i as u64);
        }
        stack.add_all(5, &[101, 102, 103, 104, 105]);
        assert_eq!(stack.get(4), 4);
        assert_eq!(stack.get(5), 101);
        assert_eq!(stack.get(6), 102);
        assert_eq!(stack.get(7), 103);
        assert_eq!(stack.get(8), 104);
        assert_eq!(stack.get(9), 105);
        assert_eq!(stack.get(10), 5);
    }
}
