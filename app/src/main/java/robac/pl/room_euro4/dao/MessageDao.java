package robac.pl.room_euro4.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import robac.pl.room_euro4.model.Message;


@Dao
public interface MessageDao {
    @Insert
    void insert(Message message);
    @Query("SELECT MAX(id) FROM wiadomosci")
    Long getMaxId();

    @Query("SELECT MIN(id) FROM wiadomosci")
    Long getMinId();
    @Query("SELECT * FROM wiadomosci order by id asc")
    List<Message> getAll();

    @Query("SELECT * FROM wiadomosci Where (isRabbitMQ=:warunek) order by id asc")
    List<Message> getAllnotSendRabbit(boolean warunek);

    @Query("SELECT * FROM wiadomosci WHERE Id = :id;")
    Maybe<Message> get(long id);
    @Query("SELECT * FROM wiadomosci WHERE Id = :id;")
    List<Message> getSingle(long id);

    @Query("SELECT * FROM wiadomosci")
    Single<List<Message>> getAllRx();
    @Query("select * from wiadomosci where qrcode = :receiver")
    List<Message> getMessagesByReceiver(String receiver);
    @Delete
    Completable delete(Message ob);

    @Query("DELETE FROM wiadomosci")
    Completable deleteAll();

    @Query("DELETE FROM wiadomosci WHERE Id = :id")
    Completable deleteid(long id);

    @Query("SELECT * FROM wiadomosci WHERE id = :id")
    Maybe<Message> getSelectedMessage(long id);

    @Query("SELECT * FROM wiadomosci")
    Observable<List<Message>> observe();
    @Query("SELECT * FROM wiadomosci WHERE id = :id")
    Observable<Message> observe(long id);
    @Query("SELECT * FROM  wiadomosci WHERE id = :id")
    public boolean isExist(Long id);

    @Update(onConflict = REPLACE)

    Completable update(Message ob);

    @Insert(onConflict = REPLACE)
    Completable add(Message ob);
//    void insert(Message message);
}
