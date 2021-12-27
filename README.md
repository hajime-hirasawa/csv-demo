# データ構造

* 会社
  * 会社コード: 文字列(32)
  * 会社名: 必須 文字列(100)
  * 事業区分: 必須 文字列(2)
  * 代表者名: 必須 文字列(50)
  * 住所: 必須 文字列(100)
  * 電話番号: 必須 文字列(11)
  * FAX: 文字列(12)
  * メールアドレス: 文字列(100)
  * ホームページ: 文字列(200)
  * 店舗情報
    * 店舗コード: 文字列(32)
    * 店舗名: 必須 文字列(100)
    * 責任者名: 必須 文字列(50)
    * 住所: 必須 文字列(100)
    * 電話番号: 必須 文字列(11)
    * FAX: 文字列(12)
    * メールアドレス: 文字列(100)
    * ホームページ: 文字列(200)
    * 店舗面積: Number
    * 駐車場: 文字列(500)
    * 支払い方法: 文字列(500)
    * 営業時間
      * 営業時間ID: Number
      * 種別(曜日: 1、日付: 2): 必須 文字列(1)
      * 指定内容(曜日または、日付): 必須 文字列(8)
      * 開始時刻: 必須 文字列(4)
      * 終了時刻: 必須 文字列(4)

# CSV
  CSV情報は会社、店舗、営業時間の個別設定・一括設定が可能
  先頭のカラムはレコード種別(会社: CP、店舗: ST、営業時間:WT)、カラム順は上記構造の順番を保持

# リクエスト
  リクエストは
  * 更新者名: 必須 文字列(50)
  * 更新メモ: 文字列(500)
  とする
