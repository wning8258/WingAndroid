package com.wning.demo.test.algorithm;

/**
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 * 输入：l1 = [1,2,4], l2 = [1,3,4]
 * 输出：[1,1,2,3,4,4]
 * 示例 2：
 *
 * 输入：l1 = [], l2 = []
 * 输出：[]
 * 示例 3：
 *
 * 输入：l1 = [], l2 = [0]
 * 输出：[0]
 *  
 *
 * 提示：
 *
 * 两个链表的节点数目范围是 [0, 50]
 * -100 <= Node.val <= 100
 * l1 和 l2 均按 非递减顺序 排列
 *
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-two-sorted-lists
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class 合并两个有序链表21 {
    public static void main(String[] args) {
        ListNode a = new ListNode(1, new ListNode(2, new ListNode(4)));
        ListNode b = new ListNode(1, new ListNode(3, new ListNode(4)));
        合并两个有序链表21 instance = new 合并两个有序链表21();
        ListNode listNode = instance.mergeTwoLists(a, b);
        instance.printResult(listNode);
    }


    public void printResult(ListNode resultNode) {
        System.out.print("[");

        while(resultNode != null) {
            System.out.print(resultNode.val+",");
            resultNode = resultNode.next;
        }
        System.out.print("]");
    }

    /**
     * 创建一个新节点，用prev指向它。
     * while当l1和l2都不为空时，比较l1和l2的值，把prev.next指向较小的值 然后移动l1和l2中较小的俩表，同时移动prev (prev = prev.next)
     * 当while结束，证明某一个列表为空了，找出不为空的另一个聊表，把prev.next指定它，prev.next = ?
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode newListNode = new ListNode();
        ListNode prev = newListNode;

        while(l1!=null && l2!= null) {
            if (l1.val < l2.val) {
                prev.next= l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2= l2.next;
            }
            //移动自己到下一个位置
            prev = prev.next;
        }
        if (l1 != null) {
            prev.next = l1;
        } else if (l2 != null) {
            prev.next = l2;
        }
        return newListNode.next;
    }
}


