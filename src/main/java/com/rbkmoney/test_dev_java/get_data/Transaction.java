package com.rbkmoney.test_dev_java.get_data;

import java.util.Date;
import java.util.Objects;

/** Класс для хранения данных транзакции
 *
 *  Примечание: сюда же можно было добавить несколько методов для сравнения объектов Transaction, что было бы удобно
 *              с одной стороны, но с другой нарушает принцип единственной обязанности
 * */

public class Transaction {

    /** ID транзакции */
    private Integer id;
    /** Сумма транзакции */
    private Float amount;
    /** Дата проведения транзакции */
    private Date date;
    /** Блок с данными */
    private String data;
    /** Комментарий к транзакции */
    private String comment;

    public Transaction() {
    }

    public Transaction(Integer id, Float amount, Date date, String data) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", date=" + date +
                ", data='" + data + '\'' +
                '}';
    }
}
