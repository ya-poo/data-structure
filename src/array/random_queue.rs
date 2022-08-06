use std::cmp::max;

use rand::Rng;

pub struct RandomQueue {
    a: Vec<u64>,
    n: usize,
}

impl RandomQueue {
    pub fn new() -> RandomQueue {
        RandomQueue { a: vec![], n: 0 }
    }

    pub fn size(&self) -> usize {
        self.n
    }

    pub fn get(&mut self) -> u64 {
        let j = rand::thread_rng().gen_range(0..self.n);
        self.a.swap(j, self.n - 1);
        let x = self.a[self.n - 1];
        self.n -= 1;

        if self.n * 3 <= self.a.len() {
            self.resize();
        }
        x
    }

    pub fn add(&mut self, x: u64) {
        if self.n + 1 > self.a.len() {
            self.resize();
        }

        self.n += 1;
        self.a[self.n - 1] = x;
    }

    fn resize(&mut self) {
        let mut next = vec![];
        next.resize(max(self.n * 2, 1), 0);
        next[..self.n].copy_from_slice(&self.a[..self.n]);

        self.a = next;
    }
}

#[cfg(test)]
mod tests {
    use crate::array::random_queue::RandomQueue;

    #[test]
    fn pop_values() {
        let mut queue = RandomQueue::new();
        for i in 0..10 {
            queue.add(i);
        }
        for _ in 0..10 {
            println!("{}", queue.get());
        }
        assert_eq!(queue.size(), 0);
    }
}
