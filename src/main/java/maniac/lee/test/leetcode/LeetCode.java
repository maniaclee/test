package maniac.lee.test.leetcode;

/**
 *
 */
public class LeetCode {
    public String reverseString(String s) {
        char[] chars = s.toCharArray();
        for (int start = 0, end = chars.length - 1; start < end; ++start, --end) {
            char temp = chars[start];
            chars[start] = chars[end];
            chars[end] = temp;
        }
        return new String(chars);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode re = new ListNode(0);
        int carry = 0;
        for (ListNode tmp = re; l1 != null || l2 != null; l1 = l1.next, l2 = l2.next) {
            if (l1 != null)
                carry += l1.val;
            if (l2 != null)
                carry += l2.val;
            tmp.next = new ListNode(carry % 10);
            carry = carry / 10;
        }
        return re;
    }


    public void testAddTwoNumbers() {

    }


    public static class ListNode {
        int val;
        ListNode next;

        public static ListNode from(int var) {
            ListNode listNode = new ListNode(0);

            return listNode;
        }

        ListNode(int x) {
            val = x;
        }
    }
}
