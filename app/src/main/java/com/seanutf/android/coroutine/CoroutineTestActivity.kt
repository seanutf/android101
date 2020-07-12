package com.seanutf.android.coroutine

import android.os.Bundle
import android.util.Log
import android.view.View
import com.seanutf.android.base.media.data.MediaItem
import com.seanutf.android.databinding.ActivityCoroutineTestBinding
import com.seanutf.android.test.TestAK
import com.seanutf.cmmonui.arch.BaseActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import javax.crypto.NoSuchPaddingException
import kotlin.coroutines.resumeWithException

class CoroutineTestActivity : BaseActivity() {

    lateinit var vb: ActivityCoroutineTestBinding


    private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityCoroutineTestBinding.inflate(this.layoutInflater)
        setContentView(vb.root)
        setViews()
    }

    fun demo() {
//        // 启动一个协程
//        GlobalScope.launch(context, Dispatchers.Default) {
//            context.a = '123'
//            loadImageFromNet()
//            println("Hello")
//        }
//
//        GlobalScope.launch(context, Dispatchers.Default) {
//            context.a
//            loadImageFromNet()
//            println("Hello")
//        }
//
//        GlobalScope1.launch {
//            loadImageFromNet()
//            println("Hello")
//        }


        GlobalScope.launch {
            doSomeThing("abc")
        }
    }

    @ExperimentalCoroutinesApi
    private suspend fun doSomeThing(itemId: String?): MediaItem =
            suspendCancellableCoroutine {
                getMediaItem(object : AAA {
                    override fun yes(item: MediaItem) {
                        it.resume(item) {
                            val aa = "fsiofgidiggi"
                        }
                    }

                    override fun no(error: String?) {
                        it.resumeWithException(NoSuchPaddingException())
                    }
                })
            }

    private fun getMediaItem(param: AAA) {

    }

    private suspend fun loadImageFromNet(): TestAK {
        delay(1000)
        return TestAK(5)
    }

    suspend fun demo1() {
        val tt = mainScope.async() {

        }

        mainScope.launch {

        }

        val tt1 = mainScope.async(Dispatchers.IO) {
            loadImageFromNet()
        }

        val result = tt1.await()


        //todo 明确方法含义
        //await 并行 协程执行依赖
        tt1.getCompleted().aa


        if (mainScope.isActive) {
            print("dfff")
        }

        mainScope.cancel()

        // 启动一个协程
        val ll = GlobalScope.launch(Dispatchers.IO, CoroutineStart.LAZY) {
            val a1 = mainScope.async(Dispatchers.IO) {
                loadImageFromNet()
            }

            val a2 = mainScope.async(Dispatchers.IO) {
                loadImageFromNet()
            }

            val a3 = mainScope.async(Dispatchers.IO) {
                loadImageFromNet()
            }

            a1.await()
            a2.await()
            a3.await()



            withContext(Dispatchers.IO) {
//                CommonPool
//                newSingleThreadContext
            }
            delay(1000)
            println("Hello")
        }

        ll.invokeOnCompletion { }

        val pp = runBlocking {

            println("Hello")
        }

        val aa = GlobalScope.async {
            withContext(Dispatchers.IO) {
//                CommonPool
//                newSingleThreadContext
            }
            delay(1000)
            println("Hello")
        }

        aa.invokeOnCompletion {

        }

        val pty = CoroutineScope(Job() + Dispatchers.IO)

        pty.async {
            ensureActive()
            yield()
        }

        pty.cancel(object : CancellationException() {
        })
    }

    private fun setViews() {
        vb.tvTest0.setOnClickListener {
            clickTest0()
        }

        vb.tvTest1.setOnClickListener {
            clickTest1()
        }

        vb.tvTest2.setOnClickListener {
            clickTest2()
        }

        vb.tvTest3.setOnClickListener {
            clickTest3()
        }

        vb.tvTest4.setOnClickListener {
            clickTest4()
        }

        vb.tvTest5.setOnClickListener {
            clickTest5()
        }

        vb.tvTest6.setOnClickListener {
            clickTest6()
        }
    }

    private fun clickTest0() {
        vb.pbLoading.visibility = View.VISIBLE
        val aa = mainScope.async {
            //val i = cusSum(0)
        }
    }

//    private fun cusSum(p: Int): Int {
//        if (p < 100) {
//
//        }
//    }

    private fun clickTest1() {

    }

    private fun clickTest2() {

    }

    private fun clickTest3() {

    }

    private fun clickTest4() {

    }

    private fun clickTest5() {

    }

    private fun clickTest6() {

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("CoroutineTest", "call onDestroy() mainScope.cancel()")
        mainScope.cancel()
    }


    override fun onResume() {
        super.onResume()
        val channel = Channel<Int>()
//        mainScope.launch {
//            // 这里可能是消耗大量 CPU 运算的异步逻辑，我们将仅仅做 5 次整数的平方并发送
//            for (x in 1..5) channel.send(x * x)
//        }
//// 这里我们打印了 5 次被接收的整数：
//        repeat(5) { println(channel.receive()) }
//        println("Done!")
    }


//    fun foo(): Flow<Int> = flow { // 流构建器
//        for (i in 1..3) {
//            delay(100) // 假装我们在这里做了一些有用的事情
//            emit(i) // 发送下一个值
//        }
//    }
//
//    @InternalCoroutinesApi
//    fun main() = runBlocking<Unit> {
//        // 启动并发的协程以验证主线程并未阻塞
//        launch {
//            for (k in 1..3) {
//                println("I'm not blocked $k")
//                delay(100)
//            }
//        }
//        // 收集这个流
//        foo().collect { value -> println(value) }
//    }
//}
//
//private fun <T> Flow<T>.collect(collector: (T) -> Unit) {
//}


    interface AAA {
        fun yes(item: MediaItem)
        fun no(error: String?)
    }
}