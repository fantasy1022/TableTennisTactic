package com.fantasyfang.tabletennistactic.repository.player

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fantasyfang.tabletennistactic.data.player.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Query("SELECT * from players ORDER BY playerIndex ASC")
    fun getAllItems(): Flow<List<Player>>

    @Query("SELECT * from players WHERE id = :id")
    fun getItem(id: Int): Flow<Player?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(player: Player)

    @Update
    suspend fun update(player: Player)

    @Query("DELETE FROM players WHERE id = :id")
    suspend fun delete(id: Int)
}