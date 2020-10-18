import java.math.BigDecimal

val bd0 = BigDecimal(0).setScale(2)
val bd1 = BigDecimal(1).setScale(2)
val bd5 = BigDecimal(5).setScale(2)
val bd100 = BigDecimal(100).setScale(2)
val bd606 = BigDecimal(606).setScale(2)
val bd1000 = BigDecimal(1_000).setScale(2)
val bd3010 = BigDecimal(3_010).setScale(2)
val bd5001 = BigDecimal(5_001).setScale(2)
val bd10000 = BigDecimal(10_000).setScale(2)

var isPrevMonthUser = false // покупал в предыдущем месяце
var totalSum: BigDecimal = bd0 // общая сумма покупок
var purchaseNum = 1 // номер покупки

fun main() {

    // покупка 1
    doPurchase(bd100)
    isPrevMonthUser = true // следующая покупка через месяц

    // покупка 2
    doPurchase(bd5001)
    isPrevMonthUser = false // перерыв в покупках

    // покупка 3
    doPurchase(bd10000)
    isPrevMonthUser = true // следующая покупка через месяц

    // покупка 4
    doPurchase(bd3010)

    // покупка 5
    doPurchase(bd606)

}

fun doPurchase(purchaseSum: BigDecimal) {
    // определяем скидку покупки 5% от предыдущей суммы
    val discount5Sum: BigDecimal = when {
        totalSum <= bd1000 -> bd0
        totalSum > bd1000 && totalSum < bd10000 -> bd100
        else -> getDiscSum(purchaseSum, bd5)
    }.setScale(2)

    // определяем ежемесячную скидку 1%
    val discount1Sum: BigDecimal =
        (if (isPrevMonthUser) getDiscSum(purchaseSum.subtract(discount5Sum), bd1) else bd0).setScale(2)

    // определяем сумму покупки
    val totalPurchaseSum: BigDecimal = purchaseSum.subtract(discount5Sum).subtract(discount1Sum).setScale(2)

    // вывод инфы
    println(
        """Покупка: $purchaseNum
    Общая сумма покупок: $totalSum
    Пользователь предыдущего месяца: $isPrevMonthUser
    Сумма покупки: $purchaseSum рублей
    После применения 5% скидки: $purchaseSum - $discount5Sum = ${purchaseSum.subtract(discount5Sum)}
    После применения 1% скидки: ${purchaseSum.subtract(discount5Sum)} - $discount1Sum = ${totalPurchaseSum}
    Общая сумма покупки: $totalPurchaseSum
    """
    )

    // обновление данных
    purchaseNum++
    totalSum += totalPurchaseSum
}

fun getDiscSum(value: BigDecimal, discountPerc: BigDecimal): BigDecimal {
    return (value / bd100 * discountPerc).setScale(2)
}        