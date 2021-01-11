#787
SELECT COUNT(DISTINCT(brandNo)) AS '商品数量'
FROM orders

#19676
SELECT COUNT(DISTINCT(orderNo)) AS '订单数' 
FROM orders


#总数据269290行
SELECT * FROM orders

#返回了269290行
#说明一个订单，一种货物 唯一对应一条数据
SELECT orderNo,brandNo 
FROM orders
GROUP BY orderNo,brandNo	
ORDER BY brandNo,orderNo


#每种商品出现的订单数
SELECT brandNo,COUNT(DISTINCT(orderNo)) AS times
FROM orders
GROUP BY brandNo
ORDER BY times




