public class SortedLinkedList implements SortedList{
    // private attribute for current alphabetical order (ASC or DESC) - default = ASC
    private boolean ascending = true;

    // private attribute for head node
    private Node head_node;

    // private attribute for list's size count
    private int list_size = 0;

    @Override
    public int size() {
        return list_size;
    }

    @Override
    public void add(String string) {
        // initialise new node with the string given
        Node node = new Node(string);
        // then use add(node) function on new node
        add(node);
    }

    @Override
    public void add(Node node) {
        // first check if string is alphabetical - return if not alphabetical
        if (!node.getString().matches("[a-zA-Z]+")) {
            return;
        }
        // 'node' - new node to be added to linked list
        // if head node does not exist (empty list), add new node as head node
        if (head_node == null) {
            head_node = node;

            // make new head node point to null on both ends as list is empty
            head_node.setPrev(null);
            head_node.setNext(null);

            // increment list size
            list_size += 1;
        }

        // otherwise (head_node exists/list not empty) traverse through linked list and insert new node where appropriate
        else {
            // first check if string of node to be added already exists in the linked list
            if (isPresent(node.getString())) {
                // return if string exists
                return;
            }

            // store strings for node to be added and head node
            String node_s = node.getString();
            String head_s = head_node.getString();

            // initialising conditions to check for ASC and DESC order
            boolean before_head = false;
            // conditions for ASC order
            if (ascending) {
                if ((node_s.compareToIgnoreCase(head_s)) < 0) {
                    before_head = true;
                }
            }
            // conditions for DESC order
            else {
                if ((node_s.compareToIgnoreCase(head_s)) > 0) {
                    before_head = true;
                }
            }

            // check if new node to add is BEFORE head node
            if (before_head) {
                // temp new node for old head node
                // head (new) - temp (old head) - next
                Node next_node = head_node.getNext();

                Node temp = new Node(head_node.getString());

                // update new head node
                head_node = node;
                head_node.setPrev(null);
                head_node.setNext(temp);

                // update temp node
                temp.setPrev(head_node);
                temp.setNext(next_node);

                // update next node
                if (next_node != null) {
                    next_node.setPrev(temp);
                }

                // increment list size
                list_size += 1;
                // return after successfully adding node
                return;
            }

            // Traversing through linked list
            // current node set to head node
            Node current_node = head_node;

            // continue traversing through list if current node exists - end of list not reached
            while (current_node != null) {
                // update next node
                Node next_node = current_node.getNext();

                // get string of the node to be added
                node_s = node.getString();
                // get string of the current node
                String current_node_s = current_node.getString();

                // if next node not null - end of list not reached
                if (next_node != null) {
                    String next_node_s = next_node.getString();

                    // store results of compare
                    int cmp_c = node_s.compareToIgnoreCase(current_node_s);
                    int cmp_n = node_s.compareToIgnoreCase(next_node_s);


                    // Current - New - Next
                    // initialise condition to check if new node is between current and new nodes
                    boolean between = false;
                    // check if between condition is true depending on the order - ASC or DESC
                    if (ascending) {
                        if ((cmp_c > 0) && (cmp_n < 0)) {
                            between = true;
                        }
                    } else {
                        if ((cmp_c < 0) && (cmp_n > 0)) {
                            between = true;
                        }
                    }

                    // check if string to add is between current and next node
                    if (between) {
                        // update new node to point to current and next node
                        node.setPrev(current_node);
                        node.setNext(next_node);
                        // update current and next node to point to new node
                        current_node.setNext(node);
                        next_node.setPrev(node);

                        // increment list size
                        list_size += 1;
                        return;
                    }

                    // otherwise string to add is AFTER both current and next node
                    // continue traversing through linked list

                }

                // otherwise end of list reached
                else {
                    // add to end of the linked list
                    // update current node to point to new node
                    current_node.setNext(node);
                    // update new node to point to current node and null (end of linked list)
                    node.setPrev(current_node);
                    node.setNext(null);
                    // increment list size
                    list_size += 1;
                    return;
                }

                // continue traversing to next node
                current_node = next_node;
            }
        }
    }

    @Override
    public Node getFirst() {
        // returns head node - first node of linked list
        return head_node;
    }

    @Override
    public Node getLast() {
        // initialise current node and next node
        // set current node at the start of traversing linked list
        Node current_node = head_node;
        // first check if linked list is empty
        if (current_node == null) {
            return null;
        }
        Node next_node = current_node.getNext();

        // traverse to last node
        while (next_node != null) {
            // update current node and next node to the next nodes in linked list
            current_node = next_node;
            next_node = current_node.getNext();
        }

        // return current node as the next node is null
        return current_node;
    }

    @Override
    public Node get(int index) {
        // before traversing check if index is out of bounds (index > list_size-1 OR index < 0)
        if (index > list_size-1 || index < 0) {
            // return null as index is out of bounds
            return null;
        }

        // otherwise proceed to retrieve node at index
        // initialise current index counter at the start of traversing
        int current_index = 0;
        // initialise current node to head node at the start of traversing
        Node current_node = head_node;

        // traverse through linked list as long as given index isn't reached yet
        while (current_index != index) {
            // traversing through linked list - update current node to next node
            current_node = current_node.getNext();
            // increment current index
            current_index += 1;
        }
        // return the current node - node at index given
        return current_node;
    }

    @Override
    public boolean isPresent(String string) {
        // set current node to head node at the start of traversing linked list
        Node current_node = head_node;
        // continue traversing through linked list as long as end of list is not reached (current node is not null)
        while (current_node != null) {
            // check if string at current node matches the string given
            if (((current_node.getString()).compareToIgnoreCase(string)) == 0) {
                return true;
            }
            // update current node to that of the next for traversing to next node
            current_node = current_node.getNext();
        }
        // return false if not found after traversing through entire linked list
        return false;
    }

    @Override
    public boolean removeFirst() {
        // if head node does not exist (empty list), cannot remove - return false
        if (head_node == null) {
            return false;
        }
        // otherwise node after head node becomes new head node
        // set previous node of new head node to null
        head_node = head_node.getNext();
        // if head node wasn't the only node in linked list update next node to be new head node
        if (head_node != null) {
            head_node.setPrev(null);
            // decrement list size
            list_size -= 1;
            // return true after removing node
            return true;
        }
        // otherwise decrement list size and return false if head node is now null - no more nodes left in list
        list_size -= 1;
        return false;
    }

    @Override
    public boolean removeLast() {
        // check if head node exists (so list is not empty)
        if (head_node == null) {
            return false;
        }
        // otherwise traverse through linked list to last node
        // initialise current node to head node at the start of traversal
        Node current_node = head_node;
        Node next_node = current_node.getNext();

        // first check if head node is the only node in the linked list
        if (next_node == null) {
            // reset head node to null
            head_node = null;
            // decrement list size
            list_size -= 1;
            // return true after "removing" head node
            return true;
        }

        // continue traversing as long as end of the list hasn't been reached
        while (next_node != null) {
            // update current & next node to that of the next in the linked list
            current_node = next_node;
            next_node = next_node.getNext();
        }

        // once end of the linked list has been reached - current_node = the last node
        // retrieve second last node
        Node prev_node = current_node.getPrev();

        // update second last node if not null
        if (prev_node != null) {
            // update second last node so its no longer pointing to the last node
            prev_node.setNext(null);
        }

        // decrement list size
        list_size -= 1;
        // return true after removing last node from linked list
        return true;
    }

    // private method to remove a node
    private boolean remove_node(Node target_node) {
        // if target node doesn't exist - index out of bounds
        if (target_node == null) {
            return false;
        }
        // if target node exists - retrieve neighbouring nodes for updating
        Node prev_node = target_node.getPrev();
        Node next_node = target_node.getNext();

        // if target node to remove is head node - update next node & reset new head node
        if (target_node == head_node) {
            // check if head node is the only node in list
            if (next_node == null) {
                // head node only node - reset head node to null
                head_node = null;
            }
            // otherwise update next node to be new head node
            else {
                next_node.setPrev(null);
                head_node = next_node;
            }
            // decrement list size
            list_size -= 1;
            // return true after updating
            return true;
        }

        // otherwise target node is not head node
        // update prev and next node to point to each other instead
        // check if prev and next node exists before updating
        if ((prev_node != null) && (next_node != null)) {
            prev_node.setNext(next_node);
            next_node.setPrev(prev_node);
        }
        // otherwise at least one of prev and next nodes does not exist
        // removing tail node: prev - target - null
        else if (prev_node != null) {
            prev_node.setNext(null);
        }

        // decrement list size
        list_size -= 1;
        // return true after updating
        return true;
    }

    @Override
    public boolean remove(int index) {
        // retrieve target node at index specified using get method
        Node target_node = get(index);
        // return return value of remove node method
        return remove_node(target_node);
    }

    @Override
    public boolean remove(String string) {
        // check if list is empty - null head node
        if (head_node == null) {
            return false;
        }
        // otherwise traverse through list to try and find node with string specified
        // not using isPresent to check if string exists because only have to traverse through list once
        Node current_node = head_node;

        while (current_node != null) {
            // check if the string at current node matches target string
            if (((current_node.getString()).compareToIgnoreCase(string)) == 0) {
                return remove_node(current_node);
            }
            // continue traversing to next node
            current_node=current_node.getNext();
        }

        // otherwise return false after traversing through whole list - string not found
        return false;
    }

    // private method to invert list - to be used by orderAscending & orderDescending
    private void invert() {
        // initialise new node for old head node
        Node prev_node = null;
        Node current_node = head_node;
        Node next_node = current_node.getNext();

        // traverse through linked list
        while (next_node != null) {
            // update nodes - inverting prev & next pointed to by current node
            prev_node = current_node.getPrev();
            next_node = current_node.getNext();
            current_node.setPrev(next_node);
            current_node.setNext(prev_node);

            // proceed to next node in list
            prev_node = current_node;
            current_node = next_node;
            next_node = current_node.getNext();
        }
        // once end of the linked list has been reached - current_node = the last node
        // update new head node
        current_node.setPrev(null);
        current_node.setNext(prev_node);
        // reset head node to that of the new head node
        head_node = current_node;
    }

    // temp to reverse
    @Override
    public void orderAscending() {
        // if list already in ascending order just return
        if (ascending) {
            return;
        }
        // if list is empty only set ascending to true then return
        if (head_node == null || list_size <= 1) {
            ascending = true;
            return;
        }

        // otherwise invert list
        invert();

        // set ascending to true
        ascending = true;
    }


    // traverse in reverse
    @Override
    public void orderDescending() {
        // return if already in descending order
        if (!ascending) {
            return;
        }

        // check if is empty or only contains head node, set ascending to false and return
        if (head_node == null || list_size <= 1) {
            ascending = false;
            return;
        }

        // otherwise invert list
        invert();

        // set ascending to false
        ascending = false;
    }

    @Override
    public void print() {
        // set current node to head node at the start of traversing
        Node current_node = head_node;

        // continue traversing as long as current node is not null
        while (current_node != null) {
            // print out string at current node on new line
            System.out.println(current_node.getString());
            // set current node to next node to continue traversing linked list
            current_node = current_node.getNext();
        }
    }

}
