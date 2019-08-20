package mg.template.core.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler

interface SchedulerProvider {
    fun ui(): Scheduler
    fun io(): Scheduler
    fun computation(): Scheduler
}

class DefaultSchedulerProvider : SchedulerProvider {
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
    override fun io() = Schedulers.io()
    override fun computation() = Schedulers.computation()
}

class TrampolineSchedulerProvider : SchedulerProvider {
    override fun ui() = Schedulers.trampoline()
    override fun io() = Schedulers.trampoline()
    override fun computation() = Schedulers.trampoline()
}

class TestSchedulerProvider(private val scheduler: TestScheduler) : SchedulerProvider {
    override fun ui() = scheduler
    override fun io() = scheduler
    override fun computation() = scheduler
}