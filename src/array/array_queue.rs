use std::cmp::max;

pub struct ArrayQueue {
    backing_array: Vec<u64>,
    j: usize,
    n: usize,
}

impl ArrayQueue {
    pub fn new() -> ArrayQueue {
        ArrayQueue {
            backing_array: vec![],
            j: 0,
            n: 0,
        }
    }

    pub fn size(&self) -> usize {
        self.n
    }

    pub fn add(&mut self, x: u64) -> bool {
        if self.n + 1 > self.backing_array.len() {
            self.resize();
        }
        let len = self.backing_array.len();
        self.backing_array[(self.j + self.n) % len] = x;
        self.n += 1;
        true
    }

    pub fn remove(&mut self) -> u64 {
        let x = self.backing_array[self.j];
        self.j = (self.j + 1) % self.backing_array.len();
        self.n -= 1;
        if self.backing_array.len() >= 3 * self.n {
            self.resize();
        }
        x
    }

    fn resize(&mut self) {
        let mut next_array = vec![];
        next_array.resize(max(self.n * 2, 1), 0);
        for k in 0..self.n {
            next_array[k] = self.backing_array[(self.j + k) % self.backing_array.len()];
        }
        self.backing_array = next_array;
        self.j = 0;
    }
}

#[cfg(test)]
mod tests {
    use crate::array::array_queue::ArrayQueue;

    #[test]
    fn success_initialize() {
        let queue = ArrayQueue::new();
        assert_eq!(queue.n, 0);
        assert_eq!(queue.j, 0);
        assert!(queue.backing_array.is_empty());
        assert_eq!(queue.size(), 0);
    }

    #[test]
    fn store_values_in_correct_order() {
        let mut queue = ArrayQueue::new();
        for i in 0..10 {
            queue.add(i);
        }
        for i in 0..10 {
            assert_eq!(queue.remove(), i);
        }
    }
}
