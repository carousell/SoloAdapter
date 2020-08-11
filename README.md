# SoloAdapter

[![CircleCI](https://circleci.com/gh/carousell/SoloAdapter.svg?style=shield)](https://circleci.com/gh/carousell/SoloAdapter)
[![jitpack](https://jitpack.io/v/carousell/SoloAdapter.svg)](https://jitpack.io/#carousell/SoloAdapter)

Adapter with single ViewHolder for Android RecyclerView.

## Install

With setup of Jitpack first, than add dependency in your build.gradle
```groovy
implementation 'com.github.carousell:SoloAdapter:0.2'
```

## Concept
Most use case is used with ConcatAdapter adapter but it's not required.
If you only want to add single View to Adapter it's too verbose to keep writing boilerplate code.
For example you want to add new header to existing Adapter, you can compose by ConcatAdapter but the new Adapter will be awkward to have single View.
Instead of manually add everything again and again, this library help you to provide a nice interface for it. 

## Usage & Example

1. Pass static View xml
```kotlin
val adapter = SoloAdapter(R.layout.some_layout)
```

2. Pass View directly
```kotlin
val view = TextView(this)

val adapter = SoloAdapter(view)
```

3. Setup bind function
```kotlin
val adapter = SoloAdapter(R.layout.some_layout)

adapter.bind { view ->
    view.findViewById<TextView>(R.id.textView).text = "some_string"
}
```

Go to ./app module for more information.

## Contributing

Thank you for being interested in contributing to this project. Check out the [CONTRIBUTING](https://github.com/carousell/SoloAdapter/blob/master/CONTRIBUTING.md) document for more info.

## About

<a href="https://github.com/carousell/" target="_blank"><img src="https://avatars2.githubusercontent.com/u/3833591" width="100px" alt="Carousell" align="right"/></a>

**SoloAdapter** is created and maintained by [Carousell](https://carousell.com/). Help us improve this project! We'd love the [feedback](https://github.com/carousell/SoloAdapter/issues) from you.

We're hiring! Find out more at <http://careers.carousell.com/>

## License

**SoloAdapter** is released under Apache License 2.0.
See [LICENSE](https://github.com/carousell/SoloAdapter/blob/master/LICENSE) for more details.