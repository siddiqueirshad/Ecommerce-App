package com.ecommerce.db;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ProductDao_Impl implements ProductDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CachedProduct> __insertionAdapterOfCachedProduct;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  public ProductDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCachedProduct = new EntityInsertionAdapter<CachedProduct>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `cached_products` (`id`,`name`,`description`,`price`,`stock`,`categoryId`,`imageUrl`,`cachedAt`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final CachedProduct entity) {
        statement.bindLong(1, entity.id);
        if (entity.name == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.name);
        }
        if (entity.description == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.description);
        }
        if (entity.price == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.price);
        }
        statement.bindLong(5, entity.stock);
        if (entity.categoryId == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.categoryId);
        }
        if (entity.imageUrl == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.imageUrl);
        }
        statement.bindLong(8, entity.cachedAt);
      }
    };
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM cached_products";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(final List<CachedProduct> products) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCachedProduct.insert(products);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insert(final CachedProduct product) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCachedProduct.insert(product);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void clearAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfClearAll.release(_stmt);
    }
  }

  @Override
  public List<CachedProduct> getAll() {
    final String _sql = "SELECT * FROM cached_products ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
      final int _cursorIndexOfStock = CursorUtil.getColumnIndexOrThrow(_cursor, "stock");
      final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
      final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
      final int _cursorIndexOfCachedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "cachedAt");
      final List<CachedProduct> _result = new ArrayList<CachedProduct>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final CachedProduct _item;
        _item = new CachedProduct();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfName)) {
          _item.name = null;
        } else {
          _item.name = _cursor.getString(_cursorIndexOfName);
        }
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _item.description = null;
        } else {
          _item.description = _cursor.getString(_cursorIndexOfDescription);
        }
        if (_cursor.isNull(_cursorIndexOfPrice)) {
          _item.price = null;
        } else {
          _item.price = _cursor.getString(_cursorIndexOfPrice);
        }
        _item.stock = _cursor.getInt(_cursorIndexOfStock);
        if (_cursor.isNull(_cursorIndexOfCategoryId)) {
          _item.categoryId = null;
        } else {
          _item.categoryId = _cursor.getLong(_cursorIndexOfCategoryId);
        }
        if (_cursor.isNull(_cursorIndexOfImageUrl)) {
          _item.imageUrl = null;
        } else {
          _item.imageUrl = _cursor.getString(_cursorIndexOfImageUrl);
        }
        _item.cachedAt = _cursor.getLong(_cursorIndexOfCachedAt);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public CachedProduct getById(final long id) {
    final String _sql = "SELECT * FROM cached_products WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
      final int _cursorIndexOfStock = CursorUtil.getColumnIndexOrThrow(_cursor, "stock");
      final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
      final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
      final int _cursorIndexOfCachedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "cachedAt");
      final CachedProduct _result;
      if (_cursor.moveToFirst()) {
        _result = new CachedProduct();
        _result.id = _cursor.getLong(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfName)) {
          _result.name = null;
        } else {
          _result.name = _cursor.getString(_cursorIndexOfName);
        }
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _result.description = null;
        } else {
          _result.description = _cursor.getString(_cursorIndexOfDescription);
        }
        if (_cursor.isNull(_cursorIndexOfPrice)) {
          _result.price = null;
        } else {
          _result.price = _cursor.getString(_cursorIndexOfPrice);
        }
        _result.stock = _cursor.getInt(_cursorIndexOfStock);
        if (_cursor.isNull(_cursorIndexOfCategoryId)) {
          _result.categoryId = null;
        } else {
          _result.categoryId = _cursor.getLong(_cursorIndexOfCategoryId);
        }
        if (_cursor.isNull(_cursorIndexOfImageUrl)) {
          _result.imageUrl = null;
        } else {
          _result.imageUrl = _cursor.getString(_cursorIndexOfImageUrl);
        }
        _result.cachedAt = _cursor.getLong(_cursorIndexOfCachedAt);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
