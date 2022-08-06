use crate::array::array_stack::ArrayStack;

pub struct RootishArrayStack {
    blocks: Vec<ArrayStack>,
    n: usize,
}

impl RootishArrayStack {
    pub fn new() -> RootishArrayStack {
        RootishArrayStack {
            blocks: vec![],
            n: 0,
        }
    }

    pub fn size(&self) -> usize {
        self.n
    }

    pub fn get(&self, i: usize) -> u64 {
        let block = RootishArrayStack::get_block(i);
        let j = i - block * (block + 1) / 2;
        self.blocks[block].get(j)
    }

    pub fn set(&mut self, i: usize, x: u64) -> u64 {
        let block = RootishArrayStack::get_block(i);
        let j = i - block * (block + 1) / 2;
        self.blocks[block].set(j, x)
    }

    pub fn add(&mut self, i: usize, x: u64) {
        let r = self.blocks.len();
        if r * (r + 1) / 2 < self.n + 1 {
            self.grow();
        }
        self.n += 1;

        for j in ((i + 1)..self.n - 1).rev() {
            self.set(j, self.get(j - 1));
        }
        self.set(i, x);
    }

    pub fn remove(&mut self, i: usize) -> u64 {
        let x = self.get(i);
        for j in i..self.n - 1 {
            self.set(j, self.get(j + 1));
        }
        self.n -= 1;

        let r = self.blocks.len();
        if (r - 2) * (r - 1) / 2 >= self.n {
            self.shrink();
        }
        x
    }

    fn get_block(i: usize) -> usize {
        let db: f32 = (-3.0 + ((9 + 8 * i) as f32).sqrt()) / 2.0;
        db.ceil() as usize
    }

    fn grow(&mut self) {
        let mut new = ArrayStack::new();
        for _ in 0..(self.blocks.len() + 1) {
            new.add(0, 0);
        }
        self.blocks.push(new);
    }

    fn shrink(&mut self) {
        let mut r = self.blocks.len();
        while r > 0 && (r - 2) * (r - 1) / 2 >= self.n {
            self.blocks.remove(self.blocks.len() - 1);
            r -= 1;
        }
    }
}

#[cfg(test)]
mod tests {
    use crate::array::rootish_array_stack::RootishArrayStack;

    #[test]
    fn success_initialize() {
        let deque = RootishArrayStack::new();
        assert_eq!(deque.size(), 0);
    }

    #[test]
    fn calculate_correct_block() {
        assert_eq!(RootishArrayStack::get_block(0), 0);
        assert_eq!(RootishArrayStack::get_block(1), 1);
        assert_eq!(RootishArrayStack::get_block(2), 1);
        assert_eq!(RootishArrayStack::get_block(3), 2);
    }

    #[test]
    fn store_values_in_correct_order() {
        let mut deque = RootishArrayStack::new();
        for i in 0..10 {
            deque.add(i, i as u64);
        }
        for i in 0..10 {
            assert_eq!(deque.get(i), i as u64)
        }
    }

    #[test]
    fn set_value() {
        let mut deque = RootishArrayStack::new();
        for i in 0..10 {
            deque.add(i, i as u64);
        }
        assert_eq!(deque.get(4), 4);
        deque.set(4, 10);
        assert_eq!(deque.get(4), 10);
    }

    #[test]
    fn remove_value() {
        let mut deque = RootishArrayStack::new();
        for i in 0..10 {
            deque.add(i, i as u64);
        }
        deque.remove(4);
        assert_eq!(deque.get(3), 3);
        assert_eq!(deque.get(4), 5);
    }
}
