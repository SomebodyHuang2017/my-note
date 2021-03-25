package main

import (
	"fmt"
)

type Weighted struct {
	Name          string `desc:"名字"`
	Weight        int32  `desc:"权重"`
	CurrentWeight int32  `desc:"当前权重"`
}

// WRR基于权重的轮训算法，参考：https://tenfy.cn/2018/11/12/smooth-weighted-round-robin/
func loadBalance(list []*Weighted) (best *Weighted) {
	total := int32(0)

	for _, w := range list {
		if w == nil {
			continue
		}

		total += w.Weight
		w.CurrentWeight += w.Weight
		if best == nil || w.CurrentWeight > best.CurrentWeight {
			best = w
		}
	}

	if best == nil {
		return
	}
	best.CurrentWeight -= total
	return
}

// 辗转相除法
func gcd(a int, b int) int {
	var temp int
	for ; b > 0; {
		temp = b
		b = a % b
		a = temp
	}
	return a
}

func main() {
	fmt.Println(gcd(10, 5))
	fmt.Println(gcd(10, 15))
	fmt.Println(gcd(10, 250))
	fmt.Println(gcd(9, 27))
	fmt.Println(gcd(10, 3))
	fmt.Println(gcd(98, 63))

	list := make([]*Weighted, 0)
	list = append(list, &Weighted{
		Name:   "a",
		Weight: 5,
	})
	list = append(list, &Weighted{
		Name:   "b",
		Weight: 10,
	})
	list = append(list, &Weighted{
		Name:   "c",
		Weight: 10,
	})

	for i := 0; i < 25; i++ {
		best := loadBalance(list)
		//fmt.Println(best)
		fmt.Print(best.Name + ", ")
	}
}
