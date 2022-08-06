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
        if self.n + 1 > self.backing_array.len() {
            self.resize()
        }
        for j in self.n..i {
            self.backing_array[j] = self.backing_array[j - 1];
        }
        self.backing_array[i] = x;
        self.n += 1;
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
}
