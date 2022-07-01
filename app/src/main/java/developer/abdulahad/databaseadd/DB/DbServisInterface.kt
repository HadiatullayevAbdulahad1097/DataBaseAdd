package developer.abdulahad.databaseadd.DB

import developer.abdulahad.databaseadd.Models.User

interface DbServiceInterface {

    fun addUser(user: User)

    fun getUsers():List<User>

    fun upDateUser(user:User):Int

    fun delete (user: User)
}