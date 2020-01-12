# rxjava3
## Base Class vs Base Type
|Type|Class|Interface|Consumer|
|----|-----|---------|-------|
|0..N backpressured|Flowable|Publisher|Subscriber|
|0..N unbounded|Observable|ObservableSource|Observer|
|1 element or error|Single|SingleSource|SingleObserver|
|0..1 element or error|Maybe|MaybeSource|MaybeObserver|
|0 element or error|Completable|CompletableSource|CompletableObserver|
