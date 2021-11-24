package com.wning.demo.test.algorithm;

/**
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 *
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 *
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[7,0,8]
 * 解释：342 + 465 = 807.
 * 示例 2：
 *
 * 输入：l1 = [0], l2 = [0]
 * 输出：[0]
 * 示例 3：
 *
 * 输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * 输出：[8,9,9,9,0,0,0,1]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 */
public class 链表两数相加 {


    public static void main(String[] args) {
        ListNode a = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode b = new ListNode(5, new ListNode(6, new ListNode(4)));

        ListNode c = new ListNode(7, new ListNode(8, new ListNode(9)));
        ListNode d = new ListNode(3, new ListNode(2));

        链表两数相加 instance = new 链表两数相加();
        instance.printResult(instance.addTwoNumbers(a, b));
        instance.printResult(instance.addTwoNumbers(c, d));
    }

    private void printResult(ListNode resultNode) {
        System.out.print("[");

        while(resultNode != null) {
            System.out.print(resultNode.val+",");
            resultNode = resultNode.next;
        }
        System.out.print("]");
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode(0);
        ListNode p = pre;
        int carry = 0;
        while(l1 != null || l2!= null) {
            int x = l1 == null ? 0 : l1.val;
            int y = l2 == null ? 0 : l2.val;
            int sum = x + y + carry;

            carry = sum/10;

            sum = sum%10;

            p.next = new ListNode(sum);
            p= p.next;

            if (l1 != null) {
                l1 =l1.next;
            }
            if (l2 != null) {
                l2 =l2.next;
            }
        }
        if(carry != 0) {  //如果最后还有进位，指向下一个
            p.next = new ListNode(carry);
        }
        //pre初始值为默认的0 node，需要切换到next
        return pre.next;
    }
}
