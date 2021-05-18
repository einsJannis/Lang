@file:Suppress("UNUSED")
package dev.einsjannis

interface Tuple<B> : Iterable<B>

interface Tuple0<B> : Tuple<B> {
    
    override fun iterator(): Iterator<B> = listOf<B>().iterator()

}

interface Tuple1<B, T0 : B> : Tuple<B> {
    
    operator fun component1(): T0

    override fun iterator(): Iterator<B> = listOf<B>(component1()).iterator()

}

interface Tuple2<B, T0 : B, T1 : B> : Tuple<B> {
    
    operator fun component1(): T0

    operator fun component2(): T1

    override fun iterator(): Iterator<B> = listOf<B>(component1(), component2()).iterator()

}

interface Tuple3<B, T0 : B, T1 : B, T2 : B> : Tuple<B> {
    
    operator fun component1(): T0

    operator fun component2(): T1

    operator fun component3(): T2

    override fun iterator(): Iterator<B> = listOf<B>(component1(), component2(), component3()).iterator()

}

interface Tuple4<B, T0 : B, T1 : B, T2 : B, T3 : B> : Tuple<B> {
    
    operator fun component1(): T0

    operator fun component2(): T1

    operator fun component3(): T2

    operator fun component4(): T3

    override fun iterator(): Iterator<B> = listOf<B>(component1(), component2(), component3(), component4()).iterator()

}

interface Tuple5<B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B> : Tuple<B> {
    
    operator fun component1(): T0

    operator fun component2(): T1

    operator fun component3(): T2

    operator fun component4(): T3

    operator fun component5(): T4

    override fun iterator(): Iterator<B> = listOf<B>(component1(), component2(), component3(), component4(), component5()).iterator()

}

interface Tuple6<B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B> : Tuple<B> {
    
    operator fun component1(): T0

    operator fun component2(): T1

    operator fun component3(): T2

    operator fun component4(): T3

    operator fun component5(): T4

    operator fun component6(): T5

    override fun iterator(): Iterator<B> = listOf<B>(component1(), component2(), component3(), component4(), component5(), component6()).iterator()

}

interface Tuple7<B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B> : Tuple<B> {
    
    operator fun component1(): T0

    operator fun component2(): T1

    operator fun component3(): T2

    operator fun component4(): T3

    operator fun component5(): T4

    operator fun component6(): T5

    operator fun component7(): T6

    override fun iterator(): Iterator<B> = listOf<B>(component1(), component2(), component3(), component4(), component5(), component6(), component7()).iterator()

}

interface Tuple8<B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B> : Tuple<B> {
    
    operator fun component1(): T0

    operator fun component2(): T1

    operator fun component3(): T2

    operator fun component4(): T3

    operator fun component5(): T4

    operator fun component6(): T5

    operator fun component7(): T6

    operator fun component8(): T7

    override fun iterator(): Iterator<B> = listOf<B>(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8()).iterator()

}

interface Tuple9<B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B> : Tuple<B> {
    
    operator fun component1(): T0

    operator fun component2(): T1

    operator fun component3(): T2

    operator fun component4(): T3

    operator fun component5(): T4

    operator fun component6(): T5

    operator fun component7(): T6

    operator fun component8(): T7

    operator fun component9(): T8

    override fun iterator(): Iterator<B> = listOf<B>(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), component9()).iterator()

}

interface Tuple10<B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B> : Tuple<B> {
    
    operator fun component1(): T0

    operator fun component2(): T1

    operator fun component3(): T2

    operator fun component4(): T3

    operator fun component5(): T4

    operator fun component6(): T5

    operator fun component7(): T6

    operator fun component8(): T7

    operator fun component9(): T8

    operator fun component10(): T9

    override fun iterator(): Iterator<B> = listOf<B>(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), component9(), component10()).iterator()

}

interface Tuple11<B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T10 : B> : Tuple<B> {
    
    operator fun component1(): T0

    operator fun component2(): T1

    operator fun component3(): T2

    operator fun component4(): T3

    operator fun component5(): T4

    operator fun component6(): T5

    operator fun component7(): T6

    operator fun component8(): T7

    operator fun component9(): T8

    operator fun component10(): T9

    operator fun component11(): T10

    override fun iterator(): Iterator<B> = listOf<B>(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), component9(), component10(), component11()).iterator()

}

interface Tuple12<B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T10 : B, T11 : B> : Tuple<B> {
    
    operator fun component1(): T0

    operator fun component2(): T1

    operator fun component3(): T2

    operator fun component4(): T3

    operator fun component5(): T4

    operator fun component6(): T5

    operator fun component7(): T6

    operator fun component8(): T7

    operator fun component9(): T8

    operator fun component10(): T9

    operator fun component11(): T10

    operator fun component12(): T11

    override fun iterator(): Iterator<B> = listOf<B>(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), component9(), component10(), component11(), component12()).iterator()

}

interface Tuple13<B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T10 : B, T11 : B, T12 : B> : Tuple<B> {
    
    operator fun component1(): T0

    operator fun component2(): T1

    operator fun component3(): T2

    operator fun component4(): T3

    operator fun component5(): T4

    operator fun component6(): T5

    operator fun component7(): T6

    operator fun component8(): T7

    operator fun component9(): T8

    operator fun component10(): T9

    operator fun component11(): T10

    operator fun component12(): T11

    operator fun component13(): T12

    override fun iterator(): Iterator<B> = listOf<B>(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), component9(), component10(), component11(), component12(), component13()).iterator()

}

interface Tuple14<B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T10 : B, T11 : B, T12 : B, T13 : B> : Tuple<B> {
    
    operator fun component1(): T0

    operator fun component2(): T1

    operator fun component3(): T2

    operator fun component4(): T3

    operator fun component5(): T4

    operator fun component6(): T5

    operator fun component7(): T6

    operator fun component8(): T7

    operator fun component9(): T8

    operator fun component10(): T9

    operator fun component11(): T10

    operator fun component12(): T11

    operator fun component13(): T12

    operator fun component14(): T13

    override fun iterator(): Iterator<B> = listOf<B>(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), component9(), component10(), component11(), component12(), component13(), component14()).iterator()

}

interface Tuple15<B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T10 : B, T11 : B, T12 : B, T13 : B, T14 : B> : Tuple<B> {
    
    operator fun component1(): T0

    operator fun component2(): T1

    operator fun component3(): T2

    operator fun component4(): T3

    operator fun component5(): T4

    operator fun component6(): T5

    operator fun component7(): T6

    operator fun component8(): T7

    operator fun component9(): T8

    operator fun component10(): T9

    operator fun component11(): T10

    operator fun component12(): T11

    operator fun component13(): T12

    operator fun component14(): T13

    operator fun component15(): T14

    override fun iterator(): Iterator<B> = listOf<B>(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), component9(), component10(), component11(), component12(), component13(), component14(), component15()).iterator()

}


fun <B> tupleOf(): Tuple0<B> = object : Tuple0<B> {
    
}

fun <B, T0 : B> tupleOf(v0: T0): Tuple1<B, T0> = object : Tuple1<B, T0> {
    
    override operator fun component1(): T0 = v0

}

fun <B, T0 : B, T1 : B> tupleOf(v0: T0, v1: T1): Tuple2<B, T0, T1> = object : Tuple2<B, T0, T1> {
    
    override operator fun component1(): T0 = v0

    override operator fun component2(): T1 = v1

}

fun <B, T0 : B, T1 : B, T2 : B> tupleOf(v0: T0, v1: T1, v2: T2): Tuple3<B, T0, T1, T2> = object : Tuple3<B, T0, T1, T2> {
    
    override operator fun component1(): T0 = v0

    override operator fun component2(): T1 = v1

    override operator fun component3(): T2 = v2

}

fun <B, T0 : B, T1 : B, T2 : B, T3 : B> tupleOf(v0: T0, v1: T1, v2: T2, v3: T3): Tuple4<B, T0, T1, T2, T3> = object : Tuple4<B, T0, T1, T2, T3> {
    
    override operator fun component1(): T0 = v0

    override operator fun component2(): T1 = v1

    override operator fun component3(): T2 = v2

    override operator fun component4(): T3 = v3

}

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B> tupleOf(v0: T0, v1: T1, v2: T2, v3: T3, v4: T4): Tuple5<B, T0, T1, T2, T3, T4> = object : Tuple5<B, T0, T1, T2, T3, T4> {
    
    override operator fun component1(): T0 = v0

    override operator fun component2(): T1 = v1

    override operator fun component3(): T2 = v2

    override operator fun component4(): T3 = v3

    override operator fun component5(): T4 = v4

}

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B> tupleOf(v0: T0, v1: T1, v2: T2, v3: T3, v4: T4, v5: T5): Tuple6<B, T0, T1, T2, T3, T4, T5> = object : Tuple6<B, T0, T1, T2, T3, T4, T5> {
    
    override operator fun component1(): T0 = v0

    override operator fun component2(): T1 = v1

    override operator fun component3(): T2 = v2

    override operator fun component4(): T3 = v3

    override operator fun component5(): T4 = v4

    override operator fun component6(): T5 = v5

}

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B> tupleOf(v0: T0, v1: T1, v2: T2, v3: T3, v4: T4, v5: T5, v6: T6): Tuple7<B, T0, T1, T2, T3, T4, T5, T6> = object : Tuple7<B, T0, T1, T2, T3, T4, T5, T6> {
    
    override operator fun component1(): T0 = v0

    override operator fun component2(): T1 = v1

    override operator fun component3(): T2 = v2

    override operator fun component4(): T3 = v3

    override operator fun component5(): T4 = v4

    override operator fun component6(): T5 = v5

    override operator fun component7(): T6 = v6

}

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B> tupleOf(v0: T0, v1: T1, v2: T2, v3: T3, v4: T4, v5: T5, v6: T6, v7: T7): Tuple8<B, T0, T1, T2, T3, T4, T5, T6, T7> = object : Tuple8<B, T0, T1, T2, T3, T4, T5, T6, T7> {
    
    override operator fun component1(): T0 = v0

    override operator fun component2(): T1 = v1

    override operator fun component3(): T2 = v2

    override operator fun component4(): T3 = v3

    override operator fun component5(): T4 = v4

    override operator fun component6(): T5 = v5

    override operator fun component7(): T6 = v6

    override operator fun component8(): T7 = v7

}

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B> tupleOf(v0: T0, v1: T1, v2: T2, v3: T3, v4: T4, v5: T5, v6: T6, v7: T7, v8: T8): Tuple9<B, T0, T1, T2, T3, T4, T5, T6, T7, T8> = object : Tuple9<B, T0, T1, T2, T3, T4, T5, T6, T7, T8> {
    
    override operator fun component1(): T0 = v0

    override operator fun component2(): T1 = v1

    override operator fun component3(): T2 = v2

    override operator fun component4(): T3 = v3

    override operator fun component5(): T4 = v4

    override operator fun component6(): T5 = v5

    override operator fun component7(): T6 = v6

    override operator fun component8(): T7 = v7

    override operator fun component9(): T8 = v8

}

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B> tupleOf(v0: T0, v1: T1, v2: T2, v3: T3, v4: T4, v5: T5, v6: T6, v7: T7, v8: T8, v9: T9): Tuple10<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9> = object : Tuple10<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9> {
    
    override operator fun component1(): T0 = v0

    override operator fun component2(): T1 = v1

    override operator fun component3(): T2 = v2

    override operator fun component4(): T3 = v3

    override operator fun component5(): T4 = v4

    override operator fun component6(): T5 = v5

    override operator fun component7(): T6 = v6

    override operator fun component8(): T7 = v7

    override operator fun component9(): T8 = v8

    override operator fun component10(): T9 = v9

}

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T10 : B> tupleOf(v0: T0, v1: T1, v2: T2, v3: T3, v4: T4, v5: T5, v6: T6, v7: T7, v8: T8, v9: T9, v10: T10): Tuple11<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> = object : Tuple11<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {
    
    override operator fun component1(): T0 = v0

    override operator fun component2(): T1 = v1

    override operator fun component3(): T2 = v2

    override operator fun component4(): T3 = v3

    override operator fun component5(): T4 = v4

    override operator fun component6(): T5 = v5

    override operator fun component7(): T6 = v6

    override operator fun component8(): T7 = v7

    override operator fun component9(): T8 = v8

    override operator fun component10(): T9 = v9

    override operator fun component11(): T10 = v10

}

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T10 : B, T11 : B> tupleOf(v0: T0, v1: T1, v2: T2, v3: T3, v4: T4, v5: T5, v6: T6, v7: T7, v8: T8, v9: T9, v10: T10, v11: T11): Tuple12<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> = object : Tuple12<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {
    
    override operator fun component1(): T0 = v0

    override operator fun component2(): T1 = v1

    override operator fun component3(): T2 = v2

    override operator fun component4(): T3 = v3

    override operator fun component5(): T4 = v4

    override operator fun component6(): T5 = v5

    override operator fun component7(): T6 = v6

    override operator fun component8(): T7 = v7

    override operator fun component9(): T8 = v8

    override operator fun component10(): T9 = v9

    override operator fun component11(): T10 = v10

    override operator fun component12(): T11 = v11

}

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T10 : B, T11 : B, T12 : B> tupleOf(v0: T0, v1: T1, v2: T2, v3: T3, v4: T4, v5: T5, v6: T6, v7: T7, v8: T8, v9: T9, v10: T10, v11: T11, v12: T12): Tuple13<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> = object : Tuple13<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> {
    
    override operator fun component1(): T0 = v0

    override operator fun component2(): T1 = v1

    override operator fun component3(): T2 = v2

    override operator fun component4(): T3 = v3

    override operator fun component5(): T4 = v4

    override operator fun component6(): T5 = v5

    override operator fun component7(): T6 = v6

    override operator fun component8(): T7 = v7

    override operator fun component9(): T8 = v8

    override operator fun component10(): T9 = v9

    override operator fun component11(): T10 = v10

    override operator fun component12(): T11 = v11

    override operator fun component13(): T12 = v12

}

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T10 : B, T11 : B, T12 : B, T13 : B> tupleOf(v0: T0, v1: T1, v2: T2, v3: T3, v4: T4, v5: T5, v6: T6, v7: T7, v8: T8, v9: T9, v10: T10, v11: T11, v12: T12, v13: T13): Tuple14<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> = object : Tuple14<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {
    
    override operator fun component1(): T0 = v0

    override operator fun component2(): T1 = v1

    override operator fun component3(): T2 = v2

    override operator fun component4(): T3 = v3

    override operator fun component5(): T4 = v4

    override operator fun component6(): T5 = v5

    override operator fun component7(): T6 = v6

    override operator fun component8(): T7 = v7

    override operator fun component9(): T8 = v8

    override operator fun component10(): T9 = v9

    override operator fun component11(): T10 = v10

    override operator fun component12(): T11 = v11

    override operator fun component13(): T12 = v12

    override operator fun component14(): T13 = v13

}

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T10 : B, T11 : B, T12 : B, T13 : B, T14 : B> tupleOf(v0: T0, v1: T1, v2: T2, v3: T3, v4: T4, v5: T5, v6: T6, v7: T7, v8: T8, v9: T9, v10: T10, v11: T11, v12: T12, v13: T13, v14: T14): Tuple15<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> = object : Tuple15<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {
    
    override operator fun component1(): T0 = v0

    override operator fun component2(): T1 = v1

    override operator fun component3(): T2 = v2

    override operator fun component4(): T3 = v3

    override operator fun component5(): T4 = v4

    override operator fun component6(): T5 = v5

    override operator fun component7(): T6 = v6

    override operator fun component8(): T7 = v7

    override operator fun component9(): T8 = v8

    override operator fun component10(): T9 = v9

    override operator fun component11(): T10 = v10

    override operator fun component12(): T11 = v11

    override operator fun component13(): T12 = v12

    override operator fun component14(): T13 = v13

    override operator fun component15(): T14 = v14

}


fun <B : BASE, T : BASE, BASE> Tuple<B>.plus(value: T): Tuple<BASE> = when (this) {
    is Tuple14<B, *, *, *, *, *, *, *, *, *, *, *, *, *, *> -> this.plus(value)
    is Tuple13<B, *, *, *, *, *, *, *, *, *, *, *, *, *> -> this.plus(value)
    is Tuple12<B, *, *, *, *, *, *, *, *, *, *, *, *> -> this.plus(value)
    is Tuple11<B, *, *, *, *, *, *, *, *, *, *, *> -> this.plus(value)
    is Tuple10<B, *, *, *, *, *, *, *, *, *, *> -> this.plus(value)
    is Tuple9<B, *, *, *, *, *, *, *, *, *> -> this.plus(value)
    is Tuple8<B, *, *, *, *, *, *, *, *> -> this.plus(value)
    is Tuple7<B, *, *, *, *, *, *, *> -> this.plus(value)
    is Tuple6<B, *, *, *, *, *, *> -> this.plus(value)
    is Tuple5<B, *, *, *, *, *> -> this.plus(value)
    is Tuple4<B, *, *, *, *> -> this.plus(value)
    is Tuple3<B, *, *, *> -> this.plus(value)
    is Tuple2<B, *, *> -> this.plus(value)
    is Tuple1<B, *> -> this.plus(value)
    is Tuple0<B, > -> this.plus(value)
    else -> throw UnsupportedOperationException()
}


operator fun <B : BASE, T : BASE, BASE> Tuple0<B>.plus(value: T) = tupleOf(value)

operator fun <B : BASE, T0 : B, T : BASE, BASE> Tuple1<B, T0>.plus(value: T) = tupleOf(component1(), value)

operator fun <B : BASE, T0 : B, T1 : B, T : BASE, BASE> Tuple2<B, T0, T1>.plus(value: T) = tupleOf(component1(), component2(), value)

operator fun <B : BASE, T0 : B, T1 : B, T2 : B, T : BASE, BASE> Tuple3<B, T0, T1, T2>.plus(value: T) = tupleOf(component1(), component2(), component3(), value)

operator fun <B : BASE, T0 : B, T1 : B, T2 : B, T3 : B, T : BASE, BASE> Tuple4<B, T0, T1, T2, T3>.plus(value: T) = tupleOf(component1(), component2(), component3(), component4(), value)

operator fun <B : BASE, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T : BASE, BASE> Tuple5<B, T0, T1, T2, T3, T4>.plus(value: T) = tupleOf(component1(), component2(), component3(), component4(), component5(), value)

operator fun <B : BASE, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T : BASE, BASE> Tuple6<B, T0, T1, T2, T3, T4, T5>.plus(value: T) = tupleOf(component1(), component2(), component3(), component4(), component5(), component6(), value)

operator fun <B : BASE, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T : BASE, BASE> Tuple7<B, T0, T1, T2, T3, T4, T5, T6>.plus(value: T) = tupleOf(component1(), component2(), component3(), component4(), component5(), component6(), component7(), value)

operator fun <B : BASE, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T : BASE, BASE> Tuple8<B, T0, T1, T2, T3, T4, T5, T6, T7>.plus(value: T) = tupleOf(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), value)

operator fun <B : BASE, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T : BASE, BASE> Tuple9<B, T0, T1, T2, T3, T4, T5, T6, T7, T8>.plus(value: T) = tupleOf(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), component9(), value)

operator fun <B : BASE, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T : BASE, BASE> Tuple10<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>.plus(value: T) = tupleOf(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), component9(), component10(), value)

operator fun <B : BASE, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T10 : B, T : BASE, BASE> Tuple11<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>.plus(value: T) = tupleOf(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), component9(), component10(), component11(), value)

operator fun <B : BASE, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T10 : B, T11 : B, T : BASE, BASE> Tuple12<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>.plus(value: T) = tupleOf(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), component9(), component10(), component11(), component12(), value)

operator fun <B : BASE, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T10 : B, T11 : B, T12 : B, T : BASE, BASE> Tuple13<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>.plus(value: T) = tupleOf(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), component9(), component10(), component11(), component12(), component13(), value)

operator fun <B : BASE, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T10 : B, T11 : B, T12 : B, T13 : B, T : BASE, BASE> Tuple14<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>.plus(value: T) = tupleOf(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), component9(), component10(), component11(), component12(), component13(), component14(), value)


fun <B, T> Tuple0<B>.castTo(constructor: () -> T): T = constructor()

fun <B, T0 : B, T> Tuple1<B, T0>.castTo(constructor: (T0) -> T): T = constructor(component1())

fun <B, T0 : B, T1 : B, T> Tuple2<B, T0, T1>.castTo(constructor: (T0, T1) -> T): T = constructor(component1(), component2())

fun <B, T0 : B, T1 : B, T2 : B, T> Tuple3<B, T0, T1, T2>.castTo(constructor: (T0, T1, T2) -> T): T = constructor(component1(), component2(), component3())

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T> Tuple4<B, T0, T1, T2, T3>.castTo(constructor: (T0, T1, T2, T3) -> T): T = constructor(component1(), component2(), component3(), component4())

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T> Tuple5<B, T0, T1, T2, T3, T4>.castTo(constructor: (T0, T1, T2, T3, T4) -> T): T = constructor(component1(), component2(), component3(), component4(), component5())

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T> Tuple6<B, T0, T1, T2, T3, T4, T5>.castTo(constructor: (T0, T1, T2, T3, T4, T5) -> T): T = constructor(component1(), component2(), component3(), component4(), component5(), component6())

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T> Tuple7<B, T0, T1, T2, T3, T4, T5, T6>.castTo(constructor: (T0, T1, T2, T3, T4, T5, T6) -> T): T = constructor(component1(), component2(), component3(), component4(), component5(), component6(), component7())

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T> Tuple8<B, T0, T1, T2, T3, T4, T5, T6, T7>.castTo(constructor: (T0, T1, T2, T3, T4, T5, T6, T7) -> T): T = constructor(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8())

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T> Tuple9<B, T0, T1, T2, T3, T4, T5, T6, T7, T8>.castTo(constructor: (T0, T1, T2, T3, T4, T5, T6, T7, T8) -> T): T = constructor(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), component9())

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T> Tuple10<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>.castTo(constructor: (T0, T1, T2, T3, T4, T5, T6, T7, T8, T9) -> T): T = constructor(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), component9(), component10())

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T10 : B, T> Tuple11<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>.castTo(constructor: (T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> T): T = constructor(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), component9(), component10(), component11())

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T10 : B, T11 : B, T> Tuple12<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>.castTo(constructor: (T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> T): T = constructor(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), component9(), component10(), component11(), component12())

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T10 : B, T11 : B, T12 : B, T> Tuple13<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>.castTo(constructor: (T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> T): T = constructor(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), component9(), component10(), component11(), component12(), component13())

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T10 : B, T11 : B, T12 : B, T13 : B, T> Tuple14<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>.castTo(constructor: (T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) -> T): T = constructor(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), component9(), component10(), component11(), component12(), component13(), component14())

fun <B, T0 : B, T1 : B, T2 : B, T3 : B, T4 : B, T5 : B, T6 : B, T7 : B, T8 : B, T9 : B, T10 : B, T11 : B, T12 : B, T13 : B, T14 : B, T> Tuple15<B, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>.castTo(constructor: (T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) -> T): T = constructor(component1(), component2(), component3(), component4(), component5(), component6(), component7(), component8(), component9(), component10(), component11(), component12(), component13(), component14(), component15())
