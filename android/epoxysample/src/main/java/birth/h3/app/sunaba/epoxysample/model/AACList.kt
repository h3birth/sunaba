package birth.h3.app.sunaba.epoxysample.model

data class AACList(
    val aacItems: List<AACItem>
) {
    companion object {
        // ref. https://developer.android.com/topic/libraries/architecture
        private val aacList = listOf(
            AACItem("LiveData", "Use LiveData to build data objects that notify views when the underlying database changes.", "https://developer.android.com/topic/libraries/architecture/livedata"),
            AACItem("ViewModel", "Stores UI-related data that isn't destroyed on app rotations.", "https://developer.android.com/topic/libraries/architecture/viewmodel"),
            AACItem("Room", "Room is an a SQLite object mapping library. Use it to Avoid boilerplate code and easily convert SQLite table data to Java objects.", "https://developer.android.com/topic/libraries/architecture/room"),
            AACItem("DataBinding", "The Data Binding Library is a support library that allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically.", "https://developer.android.com/topic/libraries/data-binding"),
            AACItem("Handling Lifecycles", "Lifecycle-aware components perform actions in response to a change in the lifecycle status of another component, such as activities and fragments.", "https://developer.android.com/topic/libraries/architecture/lifecycle"),
            AACItem("Paging library", "The Paging Library helps you load and display small chunks of data at a time. Loading partial data on demand reduces usage of network bandwidth and system resources." , "https://developer.android.com/topic/libraries/architecture/paging"),
            AACItem("WorkManager", "The WorkManager API makes it easy to schedule deferrable, asynchronous tasks that are expected to run even if the app exits or device restarts.", "https://developer.android.com/topic/libraries/architecture/workmanager")
        )
        fun getList() = AACList(aacList)
    }
}
