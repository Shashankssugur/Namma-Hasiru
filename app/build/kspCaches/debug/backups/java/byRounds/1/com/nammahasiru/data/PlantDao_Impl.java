package com.nammahasiru.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PlantDao_Impl implements PlantDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PlantEntity> __insertionAdapterOfPlantEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<PlantEntity> __updateAdapterOfPlantEntity;

  public PlantDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPlantEntity = new EntityInsertionAdapter<PlantEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `plants` (`id`,`name`,`type`,`datePlantedEpochDay`,`latitude`,`longitude`,`plantedPhotoUri`,`growthPhotoUri`,`status`,`reminderSent`,`lastUpdatedAtMillis`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PlantEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        final String _tmp = __converters.fromPlantType(entity.getType());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        statement.bindLong(4, entity.getDatePlantedEpochDay());
        if (entity.getLatitude() == null) {
          statement.bindNull(5);
        } else {
          statement.bindDouble(5, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(6);
        } else {
          statement.bindDouble(6, entity.getLongitude());
        }
        if (entity.getPlantedPhotoUri() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getPlantedPhotoUri());
        }
        if (entity.getGrowthPhotoUri() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getGrowthPhotoUri());
        }
        final String _tmp_1 = __converters.fromPlantStatus(entity.getStatus());
        if (_tmp_1 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_1);
        }
        final int _tmp_2 = entity.getReminderSent() ? 1 : 0;
        statement.bindLong(10, _tmp_2);
        statement.bindLong(11, entity.getLastUpdatedAtMillis());
      }
    };
    this.__updateAdapterOfPlantEntity = new EntityDeletionOrUpdateAdapter<PlantEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `plants` SET `id` = ?,`name` = ?,`type` = ?,`datePlantedEpochDay` = ?,`latitude` = ?,`longitude` = ?,`plantedPhotoUri` = ?,`growthPhotoUri` = ?,`status` = ?,`reminderSent` = ?,`lastUpdatedAtMillis` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PlantEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        final String _tmp = __converters.fromPlantType(entity.getType());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        statement.bindLong(4, entity.getDatePlantedEpochDay());
        if (entity.getLatitude() == null) {
          statement.bindNull(5);
        } else {
          statement.bindDouble(5, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(6);
        } else {
          statement.bindDouble(6, entity.getLongitude());
        }
        if (entity.getPlantedPhotoUri() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getPlantedPhotoUri());
        }
        if (entity.getGrowthPhotoUri() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getGrowthPhotoUri());
        }
        final String _tmp_1 = __converters.fromPlantStatus(entity.getStatus());
        if (_tmp_1 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_1);
        }
        final int _tmp_2 = entity.getReminderSent() ? 1 : 0;
        statement.bindLong(10, _tmp_2);
        statement.bindLong(11, entity.getLastUpdatedAtMillis());
        statement.bindLong(12, entity.getId());
      }
    };
  }

  @Override
  public Object upsert(final PlantEntity plant, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPlantEntity.insertAndReturnId(plant);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final PlantEntity plant, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPlantEntity.handle(plant);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PlantEntity>> getAllFlow() {
    final String _sql = "SELECT * FROM plants ORDER BY datePlantedEpochDay DESC, id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"plants"}, new Callable<List<PlantEntity>>() {
      @Override
      @NonNull
      public List<PlantEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfDatePlantedEpochDay = CursorUtil.getColumnIndexOrThrow(_cursor, "datePlantedEpochDay");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfPlantedPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "plantedPhotoUri");
          final int _cursorIndexOfGrowthPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "growthPhotoUri");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfReminderSent = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderSent");
          final int _cursorIndexOfLastUpdatedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdatedAtMillis");
          final List<PlantEntity> _result = new ArrayList<PlantEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlantEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final PlantType _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            final PlantType _tmp_1 = __converters.toPlantType(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.nammahasiru.data.PlantType', but it was NULL.");
            } else {
              _tmpType = _tmp_1;
            }
            final long _tmpDatePlantedEpochDay;
            _tmpDatePlantedEpochDay = _cursor.getLong(_cursorIndexOfDatePlantedEpochDay);
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpPlantedPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPlantedPhotoUri)) {
              _tmpPlantedPhotoUri = null;
            } else {
              _tmpPlantedPhotoUri = _cursor.getString(_cursorIndexOfPlantedPhotoUri);
            }
            final String _tmpGrowthPhotoUri;
            if (_cursor.isNull(_cursorIndexOfGrowthPhotoUri)) {
              _tmpGrowthPhotoUri = null;
            } else {
              _tmpGrowthPhotoUri = _cursor.getString(_cursorIndexOfGrowthPhotoUri);
            }
            final PlantStatus _tmpStatus;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            }
            final PlantStatus _tmp_3 = __converters.toPlantStatus(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.nammahasiru.data.PlantStatus', but it was NULL.");
            } else {
              _tmpStatus = _tmp_3;
            }
            final boolean _tmpReminderSent;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfReminderSent);
            _tmpReminderSent = _tmp_4 != 0;
            final long _tmpLastUpdatedAtMillis;
            _tmpLastUpdatedAtMillis = _cursor.getLong(_cursorIndexOfLastUpdatedAtMillis);
            _item = new PlantEntity(_tmpId,_tmpName,_tmpType,_tmpDatePlantedEpochDay,_tmpLatitude,_tmpLongitude,_tmpPlantedPhotoUri,_tmpGrowthPhotoUri,_tmpStatus,_tmpReminderSent,_tmpLastUpdatedAtMillis);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<PlantEntity>> getRecentFlow(final int limit) {
    final String _sql = "SELECT * FROM plants ORDER BY lastUpdatedAtMillis DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"plants"}, new Callable<List<PlantEntity>>() {
      @Override
      @NonNull
      public List<PlantEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfDatePlantedEpochDay = CursorUtil.getColumnIndexOrThrow(_cursor, "datePlantedEpochDay");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfPlantedPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "plantedPhotoUri");
          final int _cursorIndexOfGrowthPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "growthPhotoUri");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfReminderSent = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderSent");
          final int _cursorIndexOfLastUpdatedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdatedAtMillis");
          final List<PlantEntity> _result = new ArrayList<PlantEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlantEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final PlantType _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            final PlantType _tmp_1 = __converters.toPlantType(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.nammahasiru.data.PlantType', but it was NULL.");
            } else {
              _tmpType = _tmp_1;
            }
            final long _tmpDatePlantedEpochDay;
            _tmpDatePlantedEpochDay = _cursor.getLong(_cursorIndexOfDatePlantedEpochDay);
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpPlantedPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPlantedPhotoUri)) {
              _tmpPlantedPhotoUri = null;
            } else {
              _tmpPlantedPhotoUri = _cursor.getString(_cursorIndexOfPlantedPhotoUri);
            }
            final String _tmpGrowthPhotoUri;
            if (_cursor.isNull(_cursorIndexOfGrowthPhotoUri)) {
              _tmpGrowthPhotoUri = null;
            } else {
              _tmpGrowthPhotoUri = _cursor.getString(_cursorIndexOfGrowthPhotoUri);
            }
            final PlantStatus _tmpStatus;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            }
            final PlantStatus _tmp_3 = __converters.toPlantStatus(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.nammahasiru.data.PlantStatus', but it was NULL.");
            } else {
              _tmpStatus = _tmp_3;
            }
            final boolean _tmpReminderSent;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfReminderSent);
            _tmpReminderSent = _tmp_4 != 0;
            final long _tmpLastUpdatedAtMillis;
            _tmpLastUpdatedAtMillis = _cursor.getLong(_cursorIndexOfLastUpdatedAtMillis);
            _item = new PlantEntity(_tmpId,_tmpName,_tmpType,_tmpDatePlantedEpochDay,_tmpLatitude,_tmpLongitude,_tmpPlantedPhotoUri,_tmpGrowthPhotoUri,_tmpStatus,_tmpReminderSent,_tmpLastUpdatedAtMillis);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getById(final long id, final Continuation<? super PlantEntity> $completion) {
    final String _sql = "SELECT * FROM plants WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PlantEntity>() {
      @Override
      @Nullable
      public PlantEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfDatePlantedEpochDay = CursorUtil.getColumnIndexOrThrow(_cursor, "datePlantedEpochDay");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfPlantedPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "plantedPhotoUri");
          final int _cursorIndexOfGrowthPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "growthPhotoUri");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfReminderSent = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderSent");
          final int _cursorIndexOfLastUpdatedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdatedAtMillis");
          final PlantEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final PlantType _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            final PlantType _tmp_1 = __converters.toPlantType(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.nammahasiru.data.PlantType', but it was NULL.");
            } else {
              _tmpType = _tmp_1;
            }
            final long _tmpDatePlantedEpochDay;
            _tmpDatePlantedEpochDay = _cursor.getLong(_cursorIndexOfDatePlantedEpochDay);
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpPlantedPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPlantedPhotoUri)) {
              _tmpPlantedPhotoUri = null;
            } else {
              _tmpPlantedPhotoUri = _cursor.getString(_cursorIndexOfPlantedPhotoUri);
            }
            final String _tmpGrowthPhotoUri;
            if (_cursor.isNull(_cursorIndexOfGrowthPhotoUri)) {
              _tmpGrowthPhotoUri = null;
            } else {
              _tmpGrowthPhotoUri = _cursor.getString(_cursorIndexOfGrowthPhotoUri);
            }
            final PlantStatus _tmpStatus;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            }
            final PlantStatus _tmp_3 = __converters.toPlantStatus(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.nammahasiru.data.PlantStatus', but it was NULL.");
            } else {
              _tmpStatus = _tmp_3;
            }
            final boolean _tmpReminderSent;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfReminderSent);
            _tmpReminderSent = _tmp_4 != 0;
            final long _tmpLastUpdatedAtMillis;
            _tmpLastUpdatedAtMillis = _cursor.getLong(_cursorIndexOfLastUpdatedAtMillis);
            _result = new PlantEntity(_tmpId,_tmpName,_tmpType,_tmpDatePlantedEpochDay,_tmpLatitude,_tmpLongitude,_tmpPlantedPhotoUri,_tmpGrowthPhotoUri,_tmpStatus,_tmpReminderSent,_tmpLastUpdatedAtMillis);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUnremindedUnknownPlants(
      final Continuation<? super List<PlantEntity>> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM plants \n"
            + "        WHERE status = 'UNKNOWN'\n"
            + "        AND reminderSent = 0\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PlantEntity>>() {
      @Override
      @NonNull
      public List<PlantEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfDatePlantedEpochDay = CursorUtil.getColumnIndexOrThrow(_cursor, "datePlantedEpochDay");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfPlantedPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "plantedPhotoUri");
          final int _cursorIndexOfGrowthPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "growthPhotoUri");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfReminderSent = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderSent");
          final int _cursorIndexOfLastUpdatedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdatedAtMillis");
          final List<PlantEntity> _result = new ArrayList<PlantEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlantEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final PlantType _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            final PlantType _tmp_1 = __converters.toPlantType(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.nammahasiru.data.PlantType', but it was NULL.");
            } else {
              _tmpType = _tmp_1;
            }
            final long _tmpDatePlantedEpochDay;
            _tmpDatePlantedEpochDay = _cursor.getLong(_cursorIndexOfDatePlantedEpochDay);
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpPlantedPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPlantedPhotoUri)) {
              _tmpPlantedPhotoUri = null;
            } else {
              _tmpPlantedPhotoUri = _cursor.getString(_cursorIndexOfPlantedPhotoUri);
            }
            final String _tmpGrowthPhotoUri;
            if (_cursor.isNull(_cursorIndexOfGrowthPhotoUri)) {
              _tmpGrowthPhotoUri = null;
            } else {
              _tmpGrowthPhotoUri = _cursor.getString(_cursorIndexOfGrowthPhotoUri);
            }
            final PlantStatus _tmpStatus;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            }
            final PlantStatus _tmp_3 = __converters.toPlantStatus(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.nammahasiru.data.PlantStatus', but it was NULL.");
            } else {
              _tmpStatus = _tmp_3;
            }
            final boolean _tmpReminderSent;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfReminderSent);
            _tmpReminderSent = _tmp_4 != 0;
            final long _tmpLastUpdatedAtMillis;
            _tmpLastUpdatedAtMillis = _cursor.getLong(_cursorIndexOfLastUpdatedAtMillis);
            _item = new PlantEntity(_tmpId,_tmpName,_tmpType,_tmpDatePlantedEpochDay,_tmpLatitude,_tmpLongitude,_tmpPlantedPhotoUri,_tmpGrowthPhotoUri,_tmpStatus,_tmpReminderSent,_tmpLastUpdatedAtMillis);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
