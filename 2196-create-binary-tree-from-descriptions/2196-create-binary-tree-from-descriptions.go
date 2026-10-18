func createBinaryTree(descriptions [][]int) *TreeNode {
	nodes := make(map[int]*TreeNode)
	hasParent := make(map[int]bool)

	for _, d := range descriptions {
		parentVal, childVal, isLeft := d[0], d[1], d[2]

		if nodes[parentVal] == nil {
			nodes[parentVal] = &TreeNode{Val: parentVal}
		}
		if nodes[childVal] == nil {
			nodes[childVal] = &TreeNode{Val: childVal}
		}

		if isLeft == 1 {
			nodes[parentVal].Left = nodes[childVal]
		} else {
			nodes[parentVal].Right = nodes[childVal]
		}
		hasParent[childVal] = true
	}

	for val, node := range nodes {
		if !hasParent[val] {
			return node
		}
	}

	return nil
}