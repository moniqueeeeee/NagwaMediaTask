package com.monica.nagwamediatask.base

import androidx.lifecycle.ViewModel
import com.monica.nagwamediatask.utils.SingleLiveEvent
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

abstract class BaseViewModel(
    var subscribeOnScheduler: Scheduler?,
    var observeOnScheduler: Scheduler?
) : ViewModel() {

    var internalState = SingleLiveEvent<BaseViewState>()

    var compositeDisposable = CompositeDisposable()


    fun <T> subscribe(
        request: Observable<T>,
        success: Consumer<T>,
        error: Consumer<Throwable> = Consumer {},
    ) {
        compositeDisposable.add(
            request.subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .subscribe(success, error)
        )

    }

    fun <T> subscribeFlowable(
        request: Flowable<T>,
        onNext: Consumer<T>,
        success: Consumer<T>,
        error: Consumer<Throwable> = Consumer {},
    ) {
        compositeDisposable.add(
            request.subscribeOn(subscribeOnScheduler!!)
                .observeOn(observeOnScheduler)
                .doOnNext(onNext)
                .subscribe(success, error)
        )

    }


    override fun onCleared() {
        if (compositeDisposable.isDisposed.not()) {
            compositeDisposable.dispose()
            compositeDisposable.clear()
        }
        super.onCleared()
    }
}