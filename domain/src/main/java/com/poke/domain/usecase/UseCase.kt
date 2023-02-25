package com.poke.domain.usecase

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

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