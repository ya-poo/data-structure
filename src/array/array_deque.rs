use std::cmp::max;

pub struct ArrayDeque {
    a: Vec<u64>,
    j: usize,
    n: usize,
}

impl ArrayDeque {
    pub fn new() -> ArrayDeque {
        ArrayDeque {
            a: vec![],
            j: 0,
            n: 0,
        }
    }

    pub fn get(&self, i: usize) -> u64 {
        self.a[(self.j + i) % self.a.len()]
    }

    pub fn set(&mut self, i: usize, x: u64) -> u64 {
        let len = self.a.len();
        let y = self.a[(self.j + i) & len];
        self.a[(self.j + i) % len] = x;
        y
    }

    pub fn add(&mut self, i: usize, x: u64) {
        if self.n + 1 > self.a.len() {
            self.resize();
        }
        let len = self.a.len();
        if i < self.n / 2 {
            self.j = if self.j == 0 { len - 1 } else { self.j - 1 };
            for k in 0..i {
                self.a[(self.j + k) % len] = self.a[(self.j + k + 1) % len];
            }
        } else {
            for k in ((i + 1)..self.n).rev() {
                self.a[(self.j + k) % len] = self.a[(self.j + k - 1) % len];
            }
        }
        self.a[(self.j + i) % len] = x;
        self.n += 1;
    }

    pub fn remove(&mut self, i: usize) -> u64 {
        let len = self.a.len();
        let x = self.a[(self.j + i) % len];
        if i < self.n / 2 {
            for k in (1..=i).rev() {
                self.a[(self.j + k) % len] = self.a[(self.j + k - 1) % len];
            }
            self.j = (self.j + 1) % len;
        } else {
            for k in (i..self.n - 1).rev() {
                self.a[(self.j + k) % len] = self.a[(self.j + k + 1) % len];
            }
        }
        self.n -= 1;
        if self.n * 3 < self.a.len() {
            self.resize();
        }
        x
    }

    fn resize(&mut self) {
        let mut next_array = vec![];
        next_array.resize(max(self.n * 2, 1), 0);
        for k in 0..self.n {
            next_array[k] = self.a[(self.j + k) % self.a.len()];
        }
        self.a = next_array;
        self.j = 0;
    }
}

#[cfg(test)]
mod tests {
    use crate::array::array_deque::ArrayDeque;

    #[test]
    fn success_initialize() {
        let deque = ArrayDeque::new();
        assert_eq!(deque.n, 0);
        assert_eq!(deque.j, 0);
        assert!(deque.a.is_empty());
    }

    #[test]
    fn store_values_in_correct_order() {
        let mut deque = ArrayDeque::new();
        for i in 0..10 {
            deque.add(i, i as u64);
        }
        for i in 0..10 {
            assert_eq!(deque.get(i), i as u64);
        }
    }

    #[test]
    fn set_value() {
        let mut deque = ArrayDeque::new();
        for i in 0..10 {
            deque.add(i, i as u64);
        }
        assert_eq!(deque.get(4), 4);
        deque.set(4, 10);
        assert_eq!(deque.get(4), 10);
    }

    #[test]
    fn remove_value() {
        let mut deque = ArrayDeque::new();
        for i in 0..10 {
            deque.add(i, i as u64);
        }
        deque.remove(4);
        assert_eq!(deque.get(3), 3);
        assert_eq!(deque.get(4), 5);
    }
}
