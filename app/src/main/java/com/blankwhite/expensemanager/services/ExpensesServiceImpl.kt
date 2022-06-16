package com.blankwhite.expensemanager.services

import android.os.Build
import androidx.annotation.MainThread
import com.blankwhite.expensemanager.models.Category
import com.blankwhite.expensemanager.models.Expense
import com.blankwhite.expensemanager.services.handlers.ResultHandler
import com.blankwhite.expensemanager.utils.convertToService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.time.LocalDate
import javax.inject.Inject

class ExpensesServiceImpl @Inject constructor(private val expensesBaseService : ExpensesBaseService) : ExpensesService {

    override suspend fun getAllExpenses(handler: ResultHandler<ExpensesResponse>) {

        expensesBaseService.getAllExpenses()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(handler::onSuccess, handler::onError)
    }

    override suspend fun getAllExpensesForPeriod(year: Int, month: Int, handler: ResultHandler<ExpensesResponse>) {
        expensesBaseService.getAllExpensesForPeriod(year, month)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(handler::onSuccess, handler::onError)
    }

    override suspend fun getExpensesForCategory(category: Category, handler: ResultHandler<ExpensesResponse>) {
        expensesBaseService.getExpensesForCategory(category.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(handler::onSuccess, handler::onError)
    }

    override suspend fun addExpense(expense: Expense, handler: ResultHandler<ExpensesResponse>) {
        val body = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ExpenseAddBody(expense.convertToService())
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        expensesBaseService.addExpense(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(handler::onSuccess, handler::onError)
    }


    override suspend fun fetchCategories(handler: ResultHandler<CategoriesResponse>) {
        expensesBaseService.fetchCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(handler::onSuccess, handler::onError)
    }
}