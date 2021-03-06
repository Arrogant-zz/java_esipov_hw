# **Тестирование сборщиков мусора.**

## **UseSerialGC**

Лог файл logs/UseSerialGC.log

До падения с OutOfMemoryError заняло 305.139s

Результат парсинга лога:
```
./logs/UseSerialGC.log
Major count: 35
Major time: 24762.967999999993 ms | Avg: 707.5133714285712 ms
Major other time: 0.0 ms
Minor count: 6
Minor time: 1202.069 ms | Avg: 200.34483333333333 ms
```

## **UseG1GC**

Лог файл logs/UseG1GC.log

До падения с OutOfMemoryError заняло 298.751s

Результат парсинга лога:
```
./logs/UseG1GC.log
Major count: 17
Major time: 9350.503999999999 ms | Avg: 550.0296470588235 ms
Major other time: 12.592000000000002 ms
Minor count: 37
Minor time: 1048.7409999999995 ms | Avg: 28.34435135135134 ms
```

## **Выводы**

G1GC запускается намного чаще SerialGC, но приемущественно делает minor запуски (37 против 6).

У SerialGC наборот начиная с какого-то времени происходят только major запуски сборки мусора.

То есть остановки G1GC происходят чаще, но каждая сборка мусора в среднем меньше (на minor это почти в 10 раз меньше каждая).

Кроме того G1GC делает ещё дополнительную работу, чем явно отнимает ресурсы компьютера.

По времени работы G1GC выиграл (суммарное время minor + major) у SerialGC. Но нужно учитывать что запускался он чаще чем SerialGC.

