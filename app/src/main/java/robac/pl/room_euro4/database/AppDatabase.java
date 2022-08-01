package robac.pl.room_euro4.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import robac.pl.room_euro4.dao.MessageDao;
import robac.pl.room_euro4.model.Message;


@Database(entities = {Message.class}, version = 4, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MessageDao messageDao();
}
