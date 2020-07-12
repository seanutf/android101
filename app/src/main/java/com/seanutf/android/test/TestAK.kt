package com.seanutf.android.test

/**
 * @property name
 * @constructor gofgokfo
 * */
open class TestAK
(name:Int?){

    /**
     * @sample
     * */
    var aa:Int= 1

    fun main() {
        println("Hello world!")

        var a = 1
        // 模板中的简单名称：
        val s1 = "a is $a"

        a = 2
        // 模板中的任意表达式：
        val s2 = "${s1.replace("is", "was")}, but now is $a"
    }

    /**
     * @return fugiodfjgoid
     * @exception e fdidjfiodspf
     * @throws NullPointerException hfdsjfsdgksdfg
     * @see [main]
     *
     * */
    fun sum(a: Int, b: Int): Int {
        return a + b
    }
}

open class TestBD{
    fun getList(){

    }
}

object Test{
    val i:Int = 3
}
/**
 * @param a is a num to max
 *
 * */
fun maxOf(a: Int, b: Int): Int {
    if (a > b) {
        return a
    } else {
        return b
    }
}

fun aaaa() {

}


fun main() {
    println("Hello world!")
//    launch(Dispatchers.IO){
//
//    }
}

/*
1。什么是协程
协程就是一套由Kotlin 官方提供的线程API(问题1。对线程表示疑问？官方的轻量又体现在哪里？)
* kotlin的协程也对Thread 相关API做了一套封装
* 让我们不用过多关心线程操作，也可以方便的写出并发操作
* 虽然本质上与其他异步框架一样，但协程借助kotlin语言优势
* 比之前的方案更方便一些
* 更重要的是kotlin的协程可以用看起来同步的方式写出异步代码
* 这就是kotlin协程号称的"非阻塞式挂起"
*
* 协程最基本的功能是并发：多线程
* 后台执行
*  launch(Dispatchers.IO){
* saveToDatabase(data)
* }
*
* 前台执行
* launch(Dispatchers.Main){
* updateViews(data)
* }
*
* 这种写法简单，但它并不算是协程相对于直接使用Thread的优势
* 因为kotlin专门添加了一个函数，用来简化对Thread的直接使用
* 而kotlin的协程最大好处是你可以吧运行在不同线程的代码写在同一个代码块里
* launch(Dispatchers.Main){  //开始：主线程
*  val user = api.getUser()  网络请求：后台线程
*  tvName.text = user.name //更新UI：主线程
* }
* 它改变了并发任务的操作难度，消除了回调，多线程协作任务的操作难度就直接被抹平
* 回调式的弊端：1.对于一组数据有两个不相关的APi请求，回调后进行拼装，明明可以并行的网络请求不得不做穿行处理
* 浪费资源
* 而用协程：
* launch(Dispatchers.Main){
* val avatar = async{api.getAvatar(user)}   //获取用户头像
* val logo = async{api.getCompanyLogo(user)}   //获取公司的logo
* val merged = suspendingMerge(avatar, logo)  //合并
* show(merged) //显示
* }
*
* 用协程写出来依然是清晰的上下行代码结构，由于消除了并发任务之间的难度，协程可以让我们
* 写出复杂的并发代码
*
* 2。协程使用
* 最简单的使用：一个launch函数，里面写上代码就能切线程(问题2。切线程？？)
launch(Dispatchers.IO){
    val image = getImage(imageId)
}

这个launch函数它具体的含义是我要创建一个新的协程，并在指定线程上运行它，这个被创建
被运行的所谓协程是谁？就是你传给launch那些代码，这一段连续的代码就做一个协程
什么时候需要协程：当你需要切换线程或者指定线程的时候，要执行后台任务
launch(Dispatchers.IO){
    val image = getImage(imageId)
    launch(Dispatchers.Main){
        ivAvatar.setImageBitmap(image)
    }
}

如果只用launch()函数，协程并不能作出比直接用线程更多的事
但是协程中有个withContext()函数，这个函数可以指定线程来执行代码
并在执行完成之后，自动把线程切回来继续执行
launch(Dispatchers.Main){
    val image = withContext(Dispatchers.IO){
    getImage(imageId)
    }

    ivAvatar.setImageBitmap(image)
}

虽然写法和刚才区别不大，但如果有更多的线程切换，区别就体现出来了
由于有自动切回功能，协程消除了并发代码在协作时的嵌套
launch(Dispatchers.Main){
...
launch(Dispatchers.IO){
    ...
    launch(Dispatchers.Main){
    ...
    launch(Dispatchers.IO){
    ...
}
}
}
}

直接写成上下关系代码就可以在多线程之间协作
launch(Dispatchers.Main){
withContext(Dispatchers.IO){
...
}

...
withContext(Dispatchers.IO){
...
}
...
}

这就是协程，协作式的例程

由于消除了嵌套，可以把withContext()放在函数的里面，让它包着函数实际执行的业务代码

launch(Dispatchers.Main){
    val image = spendingGetImage(imageId)
    ivAvatar.setImageBitmap(image)

}

fun spendingGetImage(imageId:String){
withContext(Dispatchers.IO){
    getImage(imageId)
    }
}

但是直接这样写会报错，报错告诉我们withContext是一个
suspend函数，它需要在协程里被调用
或者在另一个suspend函数里被调用
在withContext()方法中又一个suspend修饰符，有这种修饰符的就叫
suspend函数：挂起函数，为了不报错，需要给我们定义的函数也要加上这个修饰符
launch(Dispatchers.Main){
    val image = spendingGetImage(imageId)
    ivAvatar.setImageBitmap(image)

}

suspend fun spendingGetImage(imageId:String){
withContext(Dispatchers.IO){
    getImage(imageId)
    }
}

suspend是kotlin 协程最核心的关键词

代码执行到suspend函数时会被suspend挂起
并且这个挂起是非阻塞式的，它不会阻挡你的线程
从哪挂起？从当前线程挂起
就是这个协程代码块从正在执行它的线程上脱离了
注意不是停下来了
，虽然suspend有暂停的意思
而是这个协程所在的线程从这行代码开始不再运行这个协程了
线程和协程分离了具体到代码是什么意思呢
先看线程
协程的代码块在线程里到了suspend函数的时候
突然执行完毕了，返回了
完毕之后线程继续做其他的事情
如果这个线程是一个后台线程，那么可能会处于空闲状态
或者执行其他后台任务，总之跟Java的线程池里的线程
在做完事之后是一样的
要么回收掉要么再利用
如果线程是主线程，会继续工作
launch(Dispatchers.Main){
    val image = spendingGetImage(imageId)
    ivAvatar.setImageBitmap(image)

}

相当于
handler.post{
...
    val image = spendingGetImage(imageId)
    ivAvatar.setImageBitmap(image)
}
任务就是协程代码，当这个协程被挂起的时候
实质上就是post（）的这个任务提前结束了继续刷新页面

handler.post{
...
    val image = spendingGetImage(imageId)
    ivAvatar.setImageBitmap(image)
}

相当于
handler.post{
...
}

再看协程
函数的代码到达挂起函数的时候被掐断了
它会从挂起函数开始继续往下执行
不过是在指定的线程
谁指定的，挂起函数指定的比如下面这个栗子里
launch(Dispatchers.Main){
    val image = spendingGetImage(imageId)
    ivAvatar.setImageBitmap(image)

}

suspend fun spendingGetImage(imageId:String){
withContext(Dispatchers.IO){
    getImage(imageId)
    }
}

就是函数内部的withContext所指定的IO线程
在挂起函数执行完成之后
协程会自动切回线程
这就是指定线程的参数不叫Thread,而是叫Dispatcher,调度器
它不光能指定协程执行的线程
还能在suspend挂起函数之后自动在切回来
当然也不一定能切回来
你可以通过设置特殊的Dispatchers，让挂起函数执行完后不切回来
挂起的定位就是：暂时切走，稍后再切回来
切回来的动作，在协程里叫做resume恢复
为什么挂起函数只能在协程里或者另一个挂起函数里面被调用？
首先，挂起之后是需要恢复的(问题3一定要吗？)
而恢复这个功能是协程的，所以如果一个挂起函数不在协程里面被调用
那么恢复功能无法实现
挂起是如何实现的
切线程时机不是发生外部挂起函数被调用的时候
而是withContext（）被调用的时候（也不是真正切线程的点）
自定义挂起函数
什么时候自定义：原则：代码块耗时就需要挂起函数（IO操作和计算工作（文件读写、网络交互、图片模糊、美化处理））
               特殊场景：本身操作并不耗时，但需要等待、延迟(问题4：类似代替Handler.postDelay()？)
怎么写
给函数加上suspend关键字，内部用withContext（）把函数内容包住即可
非阻塞式挂起
它本质上指的是"不卡线程"这件事
不卡线程就叫非阻塞式

* */



