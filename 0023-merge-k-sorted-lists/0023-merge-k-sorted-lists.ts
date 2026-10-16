
function mergeKLists(lists: Array<ListNode | null>): ListNode | null {
    if (lists.length === 0) return null;

    function mergeTwoLists(
        l1: ListNode | null,
        l2: ListNode | null
    ): ListNode | null {
        const dummy = new ListNode(0);
        let current = dummy;

        while (l1 !== null && l2 !== null) {
            if (l1.val <= l2.val) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }

            current = current.next;
        }

        current.next = l1 !== null ? l1 : l2;

        return dummy.next;
    }

    let interval = 1;

    while (interval < lists.length) {
        for (let i = 0; i + interval < lists.length; i += interval * 2) {
            lists[i] = mergeTwoLists(lists[i], lists[i + interval]);
        }

        interval *= 2;
    }

    return lists[0];
};
