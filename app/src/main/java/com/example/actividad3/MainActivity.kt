package com.example.actividad3

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

// ===============================================================================================
// SECCION 1: ENUMERADORES Y MODELOS DE DATOS (MODEL ARCHITECTURE)
// ===============================================================================================

// Enumerador para gestionar los niveles de prioridad del evento con colores y nombres descriptivos
enum class PriorityLevel(val label: String, val colorHex: Long, val weight: Int) {
    BAJA("Baja", 0xFF4CAF50, 1),
    MEDIA("Media", 0xFFFF9800, 2),
    ALTA("Alta", 0xFFF44336, 3),
    CRITICA("Critica", 0xFF9C27B0, 4)
}

// Enumerador para categorizar el tipo de evento registrado en la app
enum class EventCategory(val categoryName: String, val icon: ImageVector, val color: Color) {
    TAREA("Tarea Escolar", Icons.Default.CheckCircle, Color(0xFF2196F3)),
    RECORDATORIO("Recordatorio", Icons.Default.Notifications, Color(0xFFFF9800)),
    EXAMEN("Examen", Icons.Default.Warning, Color(0xFFE91E63)),
    PROYECTO("Proyecto", Icons.Default.Category, Color(0xFF9C27B0)),
    PERSONAL("Personal", Icons.Default.Star, Color(0xFF4CAF50))
}

// Enumerador para los criterios de filtrado en la lista principal
enum class FilterCriteria {
    TODOS, PENDIENTES, COMPLETADOS, FAVORITOS, PRIORIDAD_ALTA
}

// Enumerador para el ordenamiento de los eventos
enum class SortOption {
    FECHA_ASC, FECHA_DESC, PRIORIDAD, TITULO
}

// Clase de modelo principal que representa un evento o recordatorio dentro del sistema
data class EventItem(
    val id: String = UUID.randomUUID().toString(),
    var title: String,
    var description: String,
    var dateString: String,
    var timestamp: Long,
    var priority: PriorityLevel,
    var category: EventCategory,
    var isCompleted: Boolean = false,
    var isFavorite: Boolean = false,
    var creationDate: Long = System.currentTimeMillis()
)

// Modelo para la gestion de errores en la validacion de formularios
data class FormValidationState(
    var titleError: String? = null,
    var dateError: String? = null,
    var descriptionError: String? = null,
    var isValid: Boolean = false
)

// ===============================================================================================
// SECCION 2: SISTEMA DE TEMAS Y ESTILOS DE COLOR (MATERIAL DESIGN 3 THEME)
// ===============================================================================================

// Paleta de colores para el modo claro personalizada
private val CustomLightColorScheme = lightColorScheme(
    primary = Color(0xFF6750A4),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xEADDFF),
    onPrimaryContainer = Color(0xFF21005D),
    secondary = Color(0xFF625B71),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFE8DEF8),
    onSecondaryContainer = Color(0xFF1D192B),
    tertiary = Color(0xFF7D5260),
    onTertiary = Color(0xFFFFFFFF),
    background = Color(0xFFFEF7FF),
    onBackground = Color(0xFF1D1B20),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1D1B20),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    error = Color(0xFFB3261E),
    onError = Color(0xFFFFFFFF)
)

//(recordatorio agregar colores)
// Paleta de colores para el modo oscuro personalizada
private val CustomDarkColorScheme = darkColorScheme(
    primary = Color(0xD0BCFF),
    onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = Color(0xEADDFF),
    secondary = Color(0xCCC7DB),
    onSecondary = Color(0xFF332D41),
    secondaryContainer = Color(0xFF4A4458),
    onSecondaryContainer = Color(0xFFE8DEF8),
    tertiary = Color(0xEFB8C8),
    onTertiary = Color(0xFF492532),
    background = Color(0xFF141218),
    onBackground = Color(0xE6E1E5),
    surface = Color(0xFF211F26),
    onSurface = Color(0xE6E1E5),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    error = Color(0xFFA8C7FA),
    onError = Color(0xFF601410)
)

// Composable wrapper que envuelve toda la aplicacion para aplicar el tema seleccionado
@Composable
fun EventAppMaterialTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) CustomDarkColorScheme else CustomLightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

// ===============================================================================================
// SECCION 3: GESTION DE ESTADOS Y LOGICA DE NEGOCIO (VIEWMODEL PATTERN)
// ===============================================================================================

class MainEventViewModel : ViewModel() {

    // Estado privado mutable para la lista de eventos
    private val _eventsList = mutableStateListOf<EventItem>()
    val eventsList: List<EventItem> get() = _eventsList

    // Estado para la busqueda en tiempo real
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Estado para el filtro activo
    private val _selectedFilter = MutableStateFlow(FilterCriteria.TODOS)
    val selectedFilter: StateFlow<FilterCriteria> = _selectedFilter.asStateFlow()

    // Estado para el ordenamiento activo
    private val _selectedSort = MutableStateFlow(SortOption.FECHA_ASC)
    val selectedSort: StateFlow<SortOption> = _selectedSort.asStateFlow()

    // Estado de carga simulado para efectos de feedback visual
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        // Inicializacion con datos de prueba estructurados
        loadDummyInitialData()
    }

    // Carga de eventos iniciales para pruebas directas en emulador
    private fun loadDummyInitialData() {
        val calendar = Calendar.getInstance()

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        calendar.add(Calendar.DAY_OF_YEAR, 2)
        val fecha1 = sdf.format(calendar.time)
        val ts1 = calendar.timeInMillis

        calendar.add(Calendar.DAY_OF_YEAR, 5)
        val fecha2 = sdf.format(calendar.time)
        val ts2 = calendar.timeInMillis

        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val fecha3 = sdf.format(calendar.time)
        val ts3 = calendar.timeInMillis

        _eventsList.addAll(
            listOf(
                EventItem(
                    title = "Entrega de Proyecto Compose",
                    description = "Subir codigo unificado a Github y redactar el reporte de pruebas de TalkBack con capturas de pantalla de los prompts.",
                    dateString = fecha1,
                    timestamp = ts1,
                    priority = PriorityLevel.CRITICA,
                    category = EventCategory.PROYECTO,
                    isFavorite = true
                ),
                EventItem(
                    title = "Examen Parcial de Redes",
                    description = "Repasar configuraciones de Subnetting, VLANs y ACLs en Cisco Packet Tracer para la evaluacion teorica y practica.",
                    dateString = fecha2,
                    timestamp = ts2,
                    priority = PriorityLevel.ALTA,
                    category = EventCategory.EXAMEN,
                    isFavorite = false
                ),
                EventItem(
                    title = "Comprar Cable Ethernet Cat6",
                    description = "Ir a la tienda de electronica por un cable de 5 metros para conectar la laptop del laboratorio.",
                    dateString = fecha3,
                    timestamp = ts3,
                    priority = PriorityLevel.BAJA,
                    category = EventCategory.RECORDATORIO,
                    isCompleted = true,
                    isFavorite = false
                )
            )
        )
    }

    //(Provado funciona)
    // Metodo para agregar un nuevo evento a la lista
    fun createAndAddEvent(
        title: String,
        description: String,
        dateString: String,
        timestamp: Long,
        priority: PriorityLevel,
        category: EventCategory
    ) {
        val newEvent = EventItem(
            title = title,
            description = description,
            dateString = dateString,
            timestamp = timestamp,
            priority = priority,
            category = category
        )
        _eventsList.add(0, newEvent)
    }

    // Metodo para actualizar un evento existente
    fun updateEventItem(
        id: String,
        newTitle: String,
        newDescription: String,
        newDateString: String,
        newTimestamp: Long,
        newPriority: PriorityLevel,
        newCategory: EventCategory
    ) {
        val index = _eventsList.indexOfFirst { it.id == id }
        if (index != -1) {
            val updated = _eventsList[index].copy(
                title = newTitle,
                description = newDescription,
                dateString = newDateString,
                timestamp = newTimestamp,
                priority = newPriority,
                category = newCategory
            )
            _eventsList[index] = updated
        }
    }

    // Metodo para eliminar un evento por ID
    fun deleteEventItem(event: EventItem) {
        _eventsList.remove(event)
    }

    // Metodo para cambiar el estado de completado
    fun toggleEventCompletion(event: EventItem) {
        val index = _eventsList.indexOfFirst { it.id == event.id }
        if (index != -1) {
            val currentStatus = _eventsList[index].isCompleted
            _eventsList[index] = _eventsList[index].copy(isCompleted = !currentStatus)
        }
    }

    // Metodo para cambiar estado de favorito
    fun toggleEventFavorite(event: EventItem) {
        val index = _eventsList.indexOfFirst { it.id == event.id }
        if (index != -1) {
            val currentFav = _eventsList[index].isFavorite
            _eventsList[index] = _eventsList[index].copy(isFavorite = !currentFav)
        }
    }

    // Cambiar la busqueda
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Cambiar el filtro
    fun updateFilter(filter: FilterCriteria) {
        _selectedFilter.value = filter
    }

    // Cambiar el ordenamiento
    fun updateSort(sort: SortOption) {
        _selectedSort.value = sort
    }

    // Funcion que procesa la lista con filtros y ordenamiento aplicado
    fun getFilteredAndSortedEvents(): List<EventItem> {
        var result = _eventsList.toList()

        // Filtrado por busqueda de texto
        if (_searchQuery.value.isNotBlank()) {
            val q = _searchQuery.value.lowercase(Locale.getDefault())
            result = result.filter {
                it.title.lowercase(Locale.getDefault()).contains(q) ||
                        it.description.lowercase(Locale.getDefault()).contains(q)
            }
        }

        // Filtrado por categoria o criterio seleccionado
        result = when (_selectedFilter.value) {
            FilterCriteria.TODOS -> result
            FilterCriteria.PENDIENTES -> result.filter { !it.isCompleted }
            FilterCriteria.COMPLETADOS -> result.filter { it.isCompleted }
            FilterCriteria.FAVORITOS -> result.filter { it.isFavorite }
            FilterCriteria.PRIORIDAD_ALTA -> result.filter { it.priority == PriorityLevel.ALTA || it.priority == PriorityLevel.CRITICA }
        }

        // Ordenamiento final de la lista
        result = when (_selectedSort.value) {
            SortOption.FECHA_ASC -> result.sortedBy { it.timestamp }
            SortOption.FECHA_DESC -> result.sortedByDescending { it.timestamp }
            SortOption.PRIORIDAD -> result.sortedByDescending { it.priority.weight }
            SortOption.TITULO -> result.sortedBy { it.title.lowercase(Locale.getDefault()) }
        }

        return result
    }

    // Metodo auxiliar para limpiar todos los datos si el usuario lo requiere
    fun clearAllCompletedEvents() {
        _eventsList.removeAll { it.isCompleted }
    }
}

// ===============================================================================================
// SECCION 4: NAVEGACION DE LA APLICACION (ROUTING AND NAVIGATION)
// ===============================================================================================

sealed class AppDestination(val route: String) {
    object HomeListScreen : AppDestination("home_list_screen")
    object AddEditFormScreen : AppDestination("add_edit_form_screen?eventId={eventId}") {
        fun createRoute(eventId: String? = null): String {
            return if (eventId != null) "add_edit_form_screen?eventId=$eventId" else "add_edit_form_screen"
        }
    }
    object SettingsScreen : AppDestination("settings_screen")
}

@Composable
fun AppNavigationRouter(
    mainViewModel: MainEventViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDestination.HomeListScreen.route
    ) {
        composable(AppDestination.HomeListScreen.route) {
            MainHomeScreen(
                navController = navController,
                viewModel = mainViewModel
            )
        }

        composable(AppDestination.AddEditFormScreen.route) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            EventFormScreen(
                navController = navController,
                viewModel = mainViewModel,
                editingEventId = eventId
            )
        }

        composable(AppDestination.SettingsScreen.route) {
            AppSettingsScreen(
                navController = navController,
                viewModel = mainViewModel
            )
        }
    }
}

// ===============================================================================================
// SECCION 5: PANTALLA PRINCIPAL (HOME SCREEN WITH LAZYCOLUMN & ANIMATIONS)
// ===============================================================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainHomeScreen(
    navController: NavController,
    viewModel: MainEventViewModel
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val activeFilter by viewModel.selectedFilter.collectAsState()
    val activeSort by viewModel.selectedSort.collectAsState()
    val eventsToShow = viewModel.getFilteredAndSortedEvents()
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    var isSearchVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearchVisible) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { viewModel.updateSearchQuery(it) },
                            placeholder = { Text("Buscar evento...", fontSize = 14.sp) },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            trailingIcon = {
                                IconButton(onClick = {
                                    viewModel.updateSearchQuery("")
                                    isSearchVisible = false
                                }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Cerrar busqueda")
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent
                            )
                        )
                    } else {
                        Column {
                            Text(
                                text = "ACTIVIDAD 3",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "${eventsToShow.size} eventos listados",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                actions = {
                    if (!isSearchVisible) {
                        IconButton(onClick = { isSearchVisible = true }) {
                            Icon(Icons.Default.Search, contentDescription = "Buscar")
                        }
                    }
                    IconButton(onClick = { navController.navigate(AppDestination.SettingsScreen.route) }) {
                        Icon(Icons.Default.Info, contentDescription = "Ajustes e Informacion")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                    navController.navigate(AppDestination.AddEditFormScreen.createRoute(null))
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Crear Nuevo Evento")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Barra de Filtros Rapidos
            QuickFilterChipsRow(
                currentFilter = activeFilter,
                onFilterSelected = { viewModel.updateFilter(it) }
            )

            // Indicador de Progreso General
            val totalEvents = viewModel.eventsList.size
            val completedEvents = viewModel.eventsList.count { it.isCompleted }
            val progress = if (totalEvents > 0) completedEvents.toFloat() / totalEvents.toFloat() else 0f

            ProgressHeaderBar(
                completedCount = completedEvents,
                totalCount = totalEvents,
                progressFraction = progress
            )

            // Contenido Principal: Lista o Estado Vacio
            if (eventsToShow.isEmpty()) {
                EmptyStateView(searchQuery = searchQuery)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp, top = 8.dp, start = 12.dp, end = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        items = eventsToShow,
                        key = { it.id }
                    ) { eventItem ->
                        SwipeToDismissEventWrapper(
                            eventItem = eventItem,
                            onDeleteConfirmed = {
                                viewModel.deleteEventItem(eventItem)
                                Toast.makeText(context, "Evento eliminado correctamente", Toast.LENGTH_SHORT).show()
                            },
                            onToggleComplete = {
                                viewModel.toggleEventCompletion(eventItem)
                            },
                            onToggleFavorite = {
                                viewModel.toggleEventFavorite(eventItem)
                            },
                            onEditClicked = {
                                navController.navigate(AppDestination.AddEditFormScreen.createRoute(eventItem.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

// ===============================================================================================
// SECCION 6: COMPONENTES DE FILTRADO Y BARRA DE PROGRESO
// ===============================================================================================

@Composable
fun QuickFilterChipsRow(
    currentFilter: FilterCriteria,
    onFilterSelected: (FilterCriteria) -> Unit
) {
    val haptic = LocalHapticFeedback.current

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(FilterCriteria.entries) { filter ->
            val isSelected = filter == currentFilter
            val bgColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
            val textColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant

            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .clickable {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onFilterSelected(filter)
                    },
                color = bgColor,
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = filter.name.lowercase().capitalize(Locale.getDefault()).replace("_", " "),
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    color = textColor,
                    fontSize = 12.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun ProgressHeaderBar(
    completedCount: Int,
    totalCount: Int,
    progressFraction: Float
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progressFraction,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "progressAnimation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Progreso Global de Tareas",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "$completedCount de $totalCount completadas",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}

// ===============================================================================================
// SECCION 7: SWIPE TO DISMISS Y ANIMACIONES DE TARJETA
// ===============================================================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDismissEventWrapper(
    eventItem: EventItem,
    onDeleteConfirmed: () -> Unit,
    onToggleComplete: () -> Unit,
    onToggleFavorite: () -> Unit,
    onEditClicked: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart || dismissValue == SwipeToDismissBoxValue.StartToEnd) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onDeleteConfirmed()
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val backgroundColor by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart, SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.error
                    else -> Color.Transparent
                },
                label = "swipeBgColor"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(14.dp))
                    .background(backgroundColor)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Eliminar",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Icono Eliminar",
                        tint = Color.White
                    )
                }
            }
        },
        content = {
            ExpandableEventCard(
                eventItem = eventItem,
                onToggleComplete = onToggleComplete,
                onToggleFavorite = onToggleFavorite,
                onEditClicked = onEditClicked
            )
        }
    )
}

@Composable
fun ExpandableEventCard(
    eventItem: EventItem,
    onToggleComplete: () -> Unit,
    onToggleFavorite: () -> Unit,
    onEditClicked: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current

    // Animaciones de elevacion y escala
    val cardElevation by animateDpAsState(
        targetValue = if (isExpanded) 10.dp else 2.dp,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "cardElevation"
    )

    val alphaValue by animateFloatAsState(
        targetValue = if (eventItem.isCompleted) 0.6f else 1.0f,
        label = "completedAlpha"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(alphaValue)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
        colors = CardDefaults.cardColors(
            containerColor = if (eventItem.isCompleted)
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            else
                MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (isExpanded) MaterialTheme.colorScheme.primary else Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Encabezado principal de la tarjeta
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Checkbox de completado
                Checkbox(
                    checked = eventItem.isCompleted,
                    onCheckedChange = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onToggleComplete()
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.width(6.dp))

                // Contenido de titulo y fecha
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = eventItem.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textDecoration = if (eventItem.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = eventItem.category.icon,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                            tint = eventItem.category.color
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${eventItem.category.categoryName} • ${eventItem.dateString}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Indicador de Prioridad
                PriorityBadgeChip(priority = eventItem.priority)

                // Boton para expandir
                IconButton(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        isExpanded = !isExpanded
                    }
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = if (isExpanded) "Contraer Tarjeta" else "Expandir Tarjeta",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Cuerpo Desplegable Animado
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Divider(color = MaterialTheme.colorScheme.surfaceVariant, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Detalles de la Descripción:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = if (eventItem.description.isNotBlank()) eventItem.description else "Sin notas adicionales registradas.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Botones de accion dentro de la tarjeta expandida
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Boton de Favorito
                        IconButton(onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                            onToggleFavorite()
                        }) {
                            Icon(
                                imageVector = if (eventItem.isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                                contentDescription = "Marcar Favorito",
                                tint = if (eventItem.isFavorite) Color(0xFFFFB300) else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Boton de Editar
                        OutlinedButton(
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                                onEditClicked()
                            },
                            modifier = Modifier.height(36.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar", modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Editar", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PriorityBadgeChip(priority: PriorityLevel) {
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(Color(priority.colorHex))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = priority.label,
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// ===============================================================================================
// SECCION 8: FORMULARIO DE REGISTRO / EDICION DE EVENTOS CON VALIDACIONES
// ===============================================================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventFormScreen(
    navController: NavController,
    viewModel: MainEventViewModel,
    editingEventId: String? = null
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    // Buscar si estamos editando
    val existingEvent = remember(editingEventId) {
        viewModel.eventsList.find { it.id == editingEventId }
    }

    // Estados para los campos de texto
    var titleInput by remember { mutableStateOf(existingEvent?.title ?: "") }
    var descriptionInput by remember { mutableStateOf(existingEvent?.description ?: "") }
    var dateInput by remember { mutableStateOf(existingEvent?.dateString ?: "") }
    var selectedPriority by remember { mutableStateOf(existingEvent?.priority ?: PriorityLevel.MEDIA) }
    var selectedCategory by remember { mutableStateOf(existingEvent?.category ?: EventCategory.TAREA) }

    // Estados de errores para la validacion en tiempo real
    var titleError by remember { mutableStateOf<String?>(null) }
    var dateError by remember { mutableStateOf<String?>(null) }
    var isFormValid by remember { mutableStateOf(false) }

    // Validacion en tiempo real con LaunchedEffect
    LaunchedEffect(titleInput, dateInput) {
        // Validar Titulo
        titleError = when {
            titleInput.isEmpty() -> "El título es obligatorio"
            titleInput.trim().isEmpty() -> "El título no puede contener solo espacios"
            titleInput.length < 3 -> "El título debe tener al menos 3 caracteres"
            else -> null
        }

        // Validar Fecha (Formato AAAA-MM-DD)
        val dateRegex = Regex("^\\d{4}-\\d{2}-\\d{2}$")
        dateError = when {
            dateInput.isEmpty() -> "La fecha es obligatoria"
            !dateRegex.matches(dateInput) -> "Formato inválido. Usa AAAA-MM-DD"
            else -> null
        }

        isFormValid = titleError == null && dateError == null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (existingEvent != null) "Editar Evento" else "Nuevo Evento",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "Formulario de Registro",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Completa los campos obligatorios (*). Las validaciones se ejecutan en tiempo real.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Divider()

            // Campo de Titulo
            Column {
                OutlinedTextField(
                    value = titleInput,
                    onValueChange = { titleInput = it },
                    label = { Text("Título del Evento *") },
                    placeholder = { Text("Ej. Examen de Cálculo") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = titleError != null,
                    singleLine = true,
                    trailingIcon = {
                        if (titleError != null) {
                            Icon(Icons.Default.Warning, contentDescription = "Error", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                )
                if (titleError != null) {
                    Text(
                        text = titleError!!,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                    )
                }
            }

            // Campo de Fecha
            Column {
                OutlinedTextField(
                    value = dateInput,
                    onValueChange = { dateInput = it },
                    label = { Text("Fecha de Cumplimiento (AAAA-MM-DD) *") },
                    placeholder = { Text("2026-12-31") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = dateError != null,
                    singleLine = true,
                    trailingIcon = {
                        Icon(Icons.Default.DateRange, contentDescription = "Fecha")
                    }
                )
                if (dateError != null) {
                    Text(
                        text = dateError!!,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                    )
                }
            }

            // Seleccion de Categoria
            Text(
                text = "Categoría del Evento:",
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(EventCategory.entries) { cat ->
                    val isSelected = cat == selectedCategory
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                selectedCategory = cat
                            },
                        color = if (isSelected) cat.color else MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = cat.icon,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = cat.categoryName,
                                fontSize = 12.sp,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            // Seleccion de Prioridad
            Text(
                text = "Nivel de Prioridad:",
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PriorityLevel.entries.forEach { priority ->
                    val isSelected = priority == selectedPriority
                    OutlinedButton(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            selectedPriority = priority
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 2.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isSelected) Color(priority.colorHex) else Color.Transparent
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color(priority.colorHex)
                        )
                    ) {
                        Text(
                            text = priority.label,
                            fontSize = 10.sp,
                            color = if (isSelected) Color.White else Color(priority.colorHex),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Campo de Descripción
            OutlinedTextField(
                value = descriptionInput,
                onValueChange = { descriptionInput = it },
                label = { Text("Descripción Detallada (Opcional)") },
                placeholder = { Text("Escribe notas o detalles adicionales aquí...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Boton de Guardado
            Button(
                onClick = {
                    if (isFormValid) {
                        haptic.performHapticFeedback(HapticFeedbackType.ContextClick)

                        // Parsear timestamp simple o usar actual
                        val ts = System.currentTimeMillis()

                        if (existingEvent != null) {
                            viewModel.updateEventItem(
                                id = existingEvent.id,
                                newTitle = titleInput.trim(),
                                newDescription = descriptionInput.trim(),
                                newDateString = dateInput.trim(),
                                newTimestamp = ts,
                                newPriority = selectedPriority,
                                newCategory = selectedCategory
                            )
                            Toast.makeText(context, "Evento actualizado correctamente", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.createAndAddEvent(
                                title = titleInput.trim(),
                                description = descriptionInput.trim(),
                                dateString = dateInput.trim(),
                                timestamp = ts,
                                priority = selectedPriority,
                                category = selectedCategory
                            )
                            Toast.makeText(context, "Evento creado exitosamente", Toast.LENGTH_SHORT).show()
                        }
                        navController.popBackStack()
                    } else {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        Toast.makeText(context, "Por favor corrige los errores del formulario", Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (existingEvent != null) "Guardar Cambios" else "Registrar Evento",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ===============================================================================================
// SECCION 9: PANTALLA DE AJUSTES E INFORMACION (SETTINGS & ACCESSIBILITY REPORT)
// ===============================================================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSettingsScreen(
    navController: NavController,
    viewModel: MainEventViewModel
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Información y Ajustes", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Acerca de la Aplicación",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Desarrollo de Aplicaciones Móviles - Actividad 3",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Sistema de Gestión de Eventos y Recordatorios con animación expandible, gestos swipe-to-dismiss y validación de formularios en tiempo real.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text(
                text = "Acciones de Limpieza",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Button(
                onClick = {
                    viewModel.clearAllCompletedEvents()
                    Toast.makeText(context, "Eventos completados eliminados", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Limpiar Eventos Completados")
            }
        }
    }
}

// ===============================================================================================
// SECCION 10: VISTA ESTADO VACIO (EMPTY STATE VIEW)
// ===============================================================================================

@Composable
fun EmptyStateView(searchQuery: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (searchQuery.isNotBlank()) "No se encontraron eventos para '$searchQuery'" else "No hay eventos registrados en esta categoría.",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ===============================================================================================
// SECCION 11: ACTIVIDAD PRINCIPAL (MAIN ACTIVITY ENTRY POINT)
// ===============================================================================================

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventAppMaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigationRouter()
                }
            }
        }
    }
}

// (Recordatorio revisar Logica de estado y validaciones)
// (Recordatorio Tarjeta animada con animateContentSize)
// (Actividad 3 - App de Registro de Eventos.¿Revisar Donde iva esto ?)