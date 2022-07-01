package developer.abdulahad.databaseadd.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import developer.abdulahad.databaseadd.Models.User
import developer.abdulahad.databaseadd.utils.Constant.DB_NAME
import developer.abdulahad.databaseadd.utils.Constant.DB_VERSION
import developer.abdulahad.databaseadd.utils.Constant.ID
import developer.abdulahad.databaseadd.utils.Constant.NAME
import developer.abdulahad.databaseadd.utils.Constant.NUMBER
import developer.abdulahad.databaseadd.utils.Constant.TABLE_NAME

class MyDbHelper(context: Context) : SQLiteOpenHelper(context,DB_NAME,null, DB_VERSION) , DbServiceInterface{
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE_NAME ($ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, $NAME TEXT NOT NULL, $NUMBER TEXT NOT NULL)"

        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun addUser(user: User) {
        val dataBase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME,user.name)
        contentValues.put(NUMBER,user.number)

        dataBase.insert(TABLE_NAME, null, contentValues)
        dataBase.close()
    }

    override fun getUsers(): List<User> {
        val list = ArrayList<User>()

        val query = "select * from $TABLE_NAME"
        val dataBase = this.readableDatabase

        val cursor = dataBase.rawQuery(query, null)

        if (cursor.moveToFirst()){
            do {

                val user = User(
                     cursor.getInt(0),
                     cursor.getString(1),
                     cursor.getString(2),
                )

                list.add(user)

            }while (cursor.moveToNext())
        }
        return list
    }

    override fun upDateUser(user: User): Int {
        val database = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(ID,user.id)
        contentValues.put(NAME,user.name)
        contentValues.put(NUMBER,user.number)

        return database.update(TABLE_NAME,contentValues,"$ID = ?", arrayOf(user.id.toString()))
    }

    override fun delete(user: User) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$ID = ?", arrayOf(user.id.toString ()))
        database.close()
    }
}