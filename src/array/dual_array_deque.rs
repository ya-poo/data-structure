use crate::array::array_deque::ArrayDeque;

pub struct DualArrayDeque {
    front: ArrayDeque,
    back: ArrayDeque,
}

impl DualArrayDeque {
    pub fn new() -> DualArrayDeque {
        DualArrayDeque {
            front: ArrayDeque::new(),
            back: ArrayDeque::new(),
        }
    }

    pub fn size(&self) -> usize {
        self.front.size() + self.back.size()
    }

    pub fn get(&self, i: usize) -> u64 {
        if i < self.front.size() {
            self.front.get(self.front.size() - i - 1)
        } else {
            self.back.get(i - self.front.size())
        }
    }
    pub fn set(&mut self, i: usize, x: u64) -> u64 {
        if i < self.front.size() {
            self.front.set(self.front.size() - i - 1, x)
        } else {
            self.back.set(i - self.front.size(), x)
        }
    }

    pub fn add(&mut self, i: usize, x: u64) {
        if i < self.front.size() {
            self.front.add(self.front.size() - i, x);
        } else {
            self.back.add(i - self.front.size(), x)
        }
        self.balance();
    }

    pub fn remove(&mut self, i: usize) -> u64 {
        let x = if i < self.front.size() {
            self.front.remove(self.front.size() - i - 1)
        } else {
            self.back.remove(i - self.front.size())
        };
        self.balance();
        x
    }

    fn balance(&mut self) {
        if self.front.size() * 3 > self.back.size() && self.front.size() < self.back.size() * 3 {
            return;
        }

        let n = self.front.size() + self.back.size();

        let nf = n / 2;
        let mut af = ArrayDeque::new();
        for _ in 0..nf {
            af.add(0, 0);
        }
        for i in 0..nf {
            af.set(nf - i - 1, self.get(i));
        }

        let nb = n - nf;
        let mut ab = ArrayDeque::new();
        for _ in 0..nb {
            ab.add(0, 0);
        }
        for i in 0..nb {
            ab.set(i, self.get(nf + i));
        }
        self.front = af;
        self.back = ab;
    }
}

#[cfg(test)]
mod tests {
    use crate::array::dual_array_deque::DualArrayDeque;

    #[test]
    fn success_initialize() {
        let deque = DualArrayDeque::new();
        assert_eq!(deque.size(), 0);
        assert_eq!(deque.front.size(), 0);
        assert_eq!(deque.back.size(), 0);
    }

    #[test]
    fn store_values_in_correct_order() {
        let mut deque = DualArrayDeque::new();
        for i in 0..10 {
            deque.add(i, i as u64);
        }
        for i in 0..10 {
            assert_eq!(deque.get(i), i as u64)
        }
    }

    #[test]
    fn set_value() {
        let mut deque = DualArrayDeque::new();
        for i in 0..10 {
            deque.add(i, i as u64);
        }
        assert_eq!(deque.get(4), 4);
        deque.set(4, 10);
        assert_eq!(deque.get(4), 10);
    }

    #[test]
    fn remove_value() {
        let mut deque = DualArrayDeque::new();
        for i in 0..10 {
            deque.add(i, i as u64);
        }
        deque.remove(4);
        assert_eq!(deque.get(3), 3);
        assert_eq!(deque.get(4), 5);
    }
}
