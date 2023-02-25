package com.poke.domain.usecase

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class RequestResultUseCase<in Request, out Result> constructor(
	private val execution: CoroutineDispatcher,
	private val subscription: CoroutineDispatcher
) {

	private var job: Job? = null

	protected abstract suspend fun invoke(request: Request): Result

	fun execute(
		scope: CoroutineScope,
		request: Request,
		onResult: (Result?) -> Unit = {}
	) {
		job = scope.launch {
			withContext(execution) { runCatching { invoke(request) } }
				.onSuccess { launch(subscription) { onResult(it) } }
				.onFailure {
					Log.e("${this@RequestResultUseCase}", "Failed to launch a Coroutine Job")
					launch(subscription) { onResult(null) }
				}
		}
	}

	fun cancel() {
		cancelSafe()
	}

	private fun cancelSafe() {
		try {
			if (job?.isActive == true) {
				job?.cancel()
			}
		} catch (ex: Exception) {
			Log.e("${this@RequestResultUseCase}", "Failed to cancel a Coroutine Job")
		}
	}
}

abstract class RequestResultFlowUseCase<in Request, out Result> constructor(
	private val execution: CoroutineDispatcher,
) {

	protected abstract fun invoke(request: Request): Flow<Result>

	fun execute(request: Request): Flow<Result> {
		return invoke(request)
			.catch {
				Log.e("${this@RequestResultFlowUseCase}.", "Failed to execute flow")
			}
			.flowOn(execution)
	}
}