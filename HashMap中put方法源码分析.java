 final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        //tab变量指向哈希table
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        //判断table是否进行了初始化，没有初始化或者长度为0，则先进行resize扩容操作
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        //用键的哈希值与哈希表长度进行逻辑与操作，确定该键所在的索引位置
        //获取该索引下是否有值，如果没有值，则直接new一个节点，放到哈希表中
        //不论有没有值，将该值保存至p临时变量
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
        else { //该键所在的索引位置有值，说明产生了哈希冲突，这里采用链地址法解决哈希冲突
            Node<K,V> e; K k;
            //判断p中的Node节点是否与需要put的节点相等，这里先判断哈希，再判断键地址，再判断内容
            //将e指向p
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            else if (p instanceof TreeNode) //判断p是否为红黑树节点，如果是则进行红黑树方式存放值
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else { //走到这里，说明产生了冲突，并且该索引位置下的数据结构还是链表
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        //如果达到了变为红黑树的阈值，则将链表转化为红黑树
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            //如果e不为null，那么说明键完全相等，这时将新值覆盖旧值
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                //这个方法是回调方法，HashMap里面空实现，给LinkedHashMap使用
                afterNodeAccess(e);
                return oldValue;
            }
        }
        // 进行了结构性修改，modCount修改次数加一，这个变量为fail-fast机制提供实现
        ++modCount;
        // size加一，并且判断是否需要扩容
        if (++size > threshold)
            resize();
        // 插入新节点，提供回调方法
        afterNodeInsertion(evict);
        return null;
    }