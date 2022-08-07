use std::cell::RefCell;
use std::rc::Rc;

type Link = Option<Rc<RefCell<Node>>>;

pub struct SLList {
    pub head: Link,
    pub tail: Link,
    n: usize,
}

#[derive(Clone)]
pub struct Node {
    x: i64,
    next: Link,
}

impl SLList {
    pub fn new() -> SLList {
        SLList {
            head: None,
            tail: None,
            n: 0,
        }
    }

    pub fn size(&self) -> usize {
        self.n
    }

    pub fn push_front(&mut self, x: i64) {
        let new_node = SLList::new_node(x);
        match self.head.take() {
            None => self.tail = Some(Rc::clone(&new_node)),
            Some(link) => new_node.borrow_mut().next = Some(link),
        }
        self.n += 1;
        self.head = Some(new_node);
    }

    pub fn push_back(&mut self, x: i64) {
        let new_node = SLList::new_node(x);
        match self.tail.take() {
            None => self.head = Some(Rc::clone(&new_node)),
            Some(link) => link.borrow_mut().next = Some(Rc::clone(&new_node)),
        }
        self.n += 1;
        self.tail = Some(new_node)
    }

    pub fn pop_front(&mut self) -> Option<i64> {
        self.head.take().map(|head| {
            if let Some(next) = head.borrow_mut().next.take() {
                self.head = Some(next);
            } else {
                self.tail.take();
            }
            self.n -= 1;
            Rc::try_unwrap(head).ok().unwrap().into_inner().x
        })
    }

    pub fn new_node(x: i64) -> Rc<RefCell<Node>> {
        Rc::new(RefCell::new(Node { x, next: None }))
    }
}

#[cfg(test)]
mod tests {
    use crate::linked_list::singly_linked_list::SLList;

    #[test]
    fn initialize() {
        assert!(SLList::new().head.is_none());
    }

    #[test]
    fn push_front_and_pop() {
        let mut list = SLList::new();
        for i in 0..10 {
            list.push_front(i);
        }
        for i in (0..10).rev() {
            assert_eq!(list.pop_front().unwrap(), i);
        }
    }

    #[test]
    fn push_back_and_pop() {
        let mut list = SLList::new();
        for i in 0..10 {
            list.push_back(i);
        }
        for i in 0..10 {
            assert_eq!(list.pop_front().unwrap(), i);
        }
    }
}
