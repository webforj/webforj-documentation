package com.webforj.samples.config;

/**
 * Centralized route configuration for the WebforJ application.
 * This class maintains all route definitions to avoid hardcoding in multiple places.
 */
public final class RouteConfig {

    private RouteConfig() {}

    // Alert routes
    public static final String ALERT = "alert";
    public static final String ALERT_THEMES = "alertthemes";
    public static final String ALERT_EXPANSES = "alertexpanses";
    public static final String CLOSABLE_ALERT = "closablealert";

    // AppLayout routes
    public static final String APP_LAYOUT = "applayout";
    public static final String APP_LAYOUT_FULL_NAVBAR = "applayoutfullnavbar";
    public static final String APP_LAYOUT_MOBILE_DRAWER = "applayoutmobiledrawer";
    public static final String APP_LAYOUT_MULTIPLE_HEADERS = "applayoutmultipleheaders";
    public static final String APP_LAYOUT_STICKY_TOOLBAR = "applayoutstickytoolbar";

    // Appnav routes
    public static final String APP_NAV = "appnav";

    // BusyIndicator routes
    public static final String BUSY_DEMO = "busydemo";
    public static final String BUSY_INDICATOR = "busyindicator";
    public static final String BUSY_SPINNERS = "busyspinners";

    // Button routes
    public static final String BUTTON_DEMO = "buttondemo";
    public static final String BUTTON_THEMES = "buttonthemes";
    public static final String BUTTON_ICON = "buttonicon";
    public static final String BUTTON_EXPANSES = "buttonexpanses";
    public static final String BUTTON_EVENT = "buttonevent";
    public static final String BUTTON_DISABLE = "buttondisable";

    // Checkbox routes
    public static final String CHECKBOX_INDETERMINATE = "checkboxindeterminate";
    public static final String CHECKBOX_EXPANSE = "checkboxexpanse";
    public static final String CHECKBOX_HORIZONTAL_TEXT = "checkboxhorizontaltext";

    // Composite routes
    public static final String COMPOSITE_VIEW = "compositeview";
    public static final String BOUND_COMPONENT_DEMO = "boundcomponentdemo";

    // ColumnsLayout routes
    public static final String COLUMNS_LAYOUT = "columnslayout";
    public static final String COLUMNS_LAYOUT_SPAN_COLUMN = "columnslayoutspancolumn";
    public static final String COLUMNS_LAYOUT_BREAKPOINTS = "columnslayoutbreakpoints";
    public static final String COLUMNS_LAYOUT_FORM = "columnslayoutform";
    public static final String COLUMNS_LAYOUT_ALIGNMENT = "columnslayoutalignment";

    // Dialog routes
    public static final String DIALOG_THEMES = "dialogthemes";
    public static final String DIALOG_SECTIONS = "dialogsections";
    public static final String DIALOG_POSITIONING = "dialogpositioning";
    public static final String DIALOG_DRAGGABLE = "dialogdraggable";
    public static final String DIALOG_CLOSE = "dialogclose";
    public static final String DIALOG_BACKDROP_BLUR = "dialogbackdropblur";
    public static final String DIALOG_AUTO_FOCUS = "dialogautofocus";
    public static final String DIALOG_ALIGNMENTS = "dialogalignments";

    // Drawer routes
    public static final String DRAWER_DEMO = "drawerdemo";
    public static final String DRAWER_WELCOME = "drawerwelcome";
    public static final String DRAWER_PLACEMENT = "drawerplacement";
    public static final String DRAWER_AUTO_FOCUS = "drawerautofocus";

    // Element Composite
    public static final String QR_CODE = "qrcode";
    public static final String QR_CODE_EVENTS = "qrcodeevents";
    public static final String QR_CODE_PROPERTIES = "qrcodeproperties";

    // Element Input
    public static final String ELEMENT_INPUT_TEXT = "elementinputtext";
    public static final String ELEMENT_INPUT_DEMO = "elementinputdemo";
    public static final String ELEMENT_INPUT_EVENT = "elementinputevent";
    public static final String ELEMENT_INPUT_FUNCTION = "elementinputfunction";

    //Field
    public static final String TIME_FIELD = "timefield";
    public static final String DATE_FIELD = "datefield";
    public static final String DATE_TIME_FIELD = "datetimefield";
    public static final String COLOR_FIELD = "colorfield";
    public static final String NUMBER_FIELD = "numberfield";
    public static final String TEXT_FIELD = "textfield";
    public static final String PASSWORD_FIELD = "passwordfield";

    public static final String MASKED_DATE_FIELD = "maskeddatefield";
    public static final String MASKED_DATE_FIELD_RESTORE = "maskeddatefieldrestore";
    public static final String MASKED_DATE_FIELD_PICKER = "maskeddatefieldpicker";
    public static final String MASKED_DATE_FIELD_SPINNER = "maskeddatefieldspinner";

    public static final String MASKED_NUMBER_FIELD_RESTORE = "maskednumberfieldrestore";
    public static final String MASKED_NUMBER_FIELD_SPINNER = "maskednumberfieldspinner";
    public static final String MASKED_NUMBER_FIELD_NEGATABLE = "maskednumberfieldnegatable";
    public static final String MASKED_NUMBER_FIELD_VIEW = "maskednumberfieldview";

    public static final String MASKED_TEXT_FIELD = "maskedtextfield";
    public static final String MASKED_TEXT_FIELD_RESTORE = "maskedtextfieldrestore";
    public static final String MASKED_TEXT_FIELD_SPINNER = "maskedtextfieldspinner";

    public static final String MASKED_TIME_FIELD_RESTORE = "maskedtimefieldrestore";
    public static final String MASKED_TIME_FIELD_SPINNER = "maskedtimefieldspinner";
    public static final String MASKED_TIME_FIELD_PICKER = "maskedtimefieldpicker";
    public static final String MASKED_TIME_FIELD_VIEW = "maskedtimefieldview";

    //Flexy Layout
    public static final String FLEX_CONTAINER_BUILDER = "flexcontainerbuilder";
    public static final String FLEX_DIRECTION = "flexdirection";
    public static final String FLEX_POSITIONING = "flexpositioning";
    public static final String FLEX_WRAPPING = "flexwrapping";
    public static final String FLEX_ORDER = "flexorder";
    public static final String FLEX_SELF_ALIGN = "flexselfalign";
    public static final String FLEX_BASIS_VIEW = "flexbasisview";
    public static final String FLEX_LAYOUT_VIEW = "flexlayoutview";

    // GoogleCharts routes
    public static final String CHART_VIEW = "chartview";
    public static final String CHART_GALLERY = "chartgallery";
    public static final String CHART_REDRAW = "chartredraw";
    public static final String CHART_SETTING_DATA = "chartsettingdata";

    // Icon routes
    public static final String ICON_BASICS = "iconbasics";
    public static final String ICON_VARIATIONS = "iconvariations";
    public static final String ICON_PREFIX_SUFFIX = "iconprefixsuffix";

    //Infiniti Scroll
    public static final String INFINITE_SCROLL_CUSTOM_LOADING = "infinitescrollcustomloading";
    public static final String INFINITE_SCROLL_LOADING = "infinitescrollloading";
    public static final String INFINITE_SCROLL_VIEW = "infinitescrollview";

    //List
    public static final String CHOICE_BOX_DROPDOWN = "choiceboxdropdown";
    public static final String CHOICE_BOX_MAX_ROW = "choiceboxmaxrow";
    public static final String COMBO_BOX_CUSTOM_VALUE = "comboboxcustomvalue";
    public static final String COMBO_BOX_DROPDOWN = "comboboxdropdown";
    public static final String COMBO_BOX_MAX_ROW = "comboboxmaxrow";
    public static final String COMBO_BOX_PLACEHOLDER = "comboboxplaceholder";
    public static final String LIST_BOX_MULTIPLE_SELECTION = "listboxmultipleselection";

    // Loading routes
    public static final String LOADING_DEMO = "loadingdemo";
    public static final String LOADING_SPINNER_DEMO = "loadingspinnerdemo";

    // Login routes
    public static final String LOGIN_BASIC = "loginbasic";
    public static final String LOGIN_CANCEL_BUTTON = "logincancelbutton";
    public static final String LOGIN_CUSTOM_FIELDS = "logincustomfields";
    public static final String LOGIN_INTERNATIONALIZATION = "logininternationalization";
    public static final String LOGIN_SUBMISSION = "loginsubmission";

    // Navigator routes
    public static final String NAVIGATOR_BASIC = "navigatorbasic";
    public static final String NAVIGATOR_LAYOUT = "navigatorlayout";
    public static final String NAVIGATOR_PAGES = "navigatorpages";
    public static final String NAVIGATOR_TABLE = "navigatortable";

    // Option-Dialog → Confirm
    public static final String CONFIRM_DIALOG_CONSTRUCTOR = "confirmdialogconstructor";
    public static final String CONFIRM_DIALOG_OPTIONS     = "confirmdialogoptions";

    // Option-Dialog → File-Chooser
    public static final String FILE_CHOOSER_DIALOG_BASIC   = "filechooserdialogbasic";
    public static final String FILE_CHOOSER_DIALOG_FILTERS = "filechooserdialogfilters";

    // Option-Dialog → File-Save
    public static final String FILE_SAVE_DIALOG_BASIC   = "filesavedialogbasic";
    public static final String FILE_SAVE_DIALOG_FILTERS = "filesavedialogfilters";

    // Option-Dialog → File-Upload
    public static final String FILE_UPLOAD_DIALOG_BASIC = "fileuploaddialogbasic";

    // Option-Dialog → Input
    public static final String INPUT_DIALOG_BASIC = "inputdialogbasic";
    public static final String INPUT_DIALOG_TYPE  = "inputdialogtype";

    // Option-Dialog → Message
    public static final String MESSAGE_DIALOG_TYPE = "messagedialogtype";

    // Progress-Bar routes
    public static final String PROGRESS_BAR_BASIC       = "progressbarbasic";
    public static final String PROGRESS_BAR_DETERMINATE = "progressbardeterminate";
    public static final String PROGRESS_BAR_ORIENTATION = "progressbarorientation";
    public static final String PROGRESS_BAR_THEMES      = "progressbarthemes";

    // Radio-Button routes
    public static final String RADIO_BUTTON_ACTIVATION = "radiobuttonactivation";
    public static final String RADIO_BUTTON_GROUP      = "radiobuttongroup";
    public static final String RADIO_BUTTON_SWITCH     = "radiobuttonswitch";
    public static final String RADIO_BUTTON_TEXT       = "radiobuttontext";

    // Refresher routes
    public static final String REFRESHER_I18N   = "refresheri18n";
    public static final String REFRESHER_ICON   = "refreshericon";
    public static final String REFRESHER_THEMES = "refresherthemes";
    public static final String REFRESHER        = "refresher";

    // Slider routes
    public static final String DONATION_SLIDER      = "donationslider";
    public static final String SLIDER_BRIGHTNESS    = "sliderbrightness";
    public static final String SLIDER_LABELS        = "sliderlabels";
    public static final String SLIDER_ORIENTATION   = "sliderorientation";
    public static final String SLIDER_TEMP          = "slidertemp";
    public static final String SLIDER_THEMES        = "sliderthemes";
    public static final String SLIDER_TICK_SPACING  = "slidertickspacing";
    public static final String SLIDER_VIEW = "sliderview";

    // Spinner routes
    public static final String SPINNER_DEMO             = "spinnerdemo";
    public static final String SPINNER_DIRECTION_DEMO   = "spinnerdirectiondemo";
    public static final String SPINNER_EXPANSE_DEMO     = "spinnerexpansedemo";
    public static final String SPINNER_SPEED_DEMO       = "spinnerspeeddemo";
    public static final String SPINNER_THEME_DEMO       = "spinnerthemedemo";

    // Splitter routes
    public static final String SPLITTER_AUTO_SAVE   = "splitterautosave";
    public static final String SPLITTER_BASIC       = "splitterbasic";
    public static final String SPLITTER_MIN_MAX     = "splitterminmax";
    public static final String SPLITTER_NESTED      = "splitternested";
    public static final String SPLITTER_ORIENTATION = "splitterorientation";
    public static final String SPLITTER_POSITION    = "splitterposition";

    // Tabbed-Pane routes
    public static final String TABBED_PANE_ACTIVATION    = "tabbedpaneactivation";
    public static final String TABBED_PANE_ALIGNMENT     = "tabbedpanealignment";
    public static final String TABBED_PANE_BORDER        = "tabbedpaneborder";
    public static final String TABBED_PANE_EXPANSE_THEME = "tabbedpaneexpansetheme";
    public static final String TABBED_PANE_PLACEMENT     = "tabbedpaneplacement";

    // Table routes
    public static final String DATA_TABLE              = "datatable";
    public static final String TABLE_BASIC             = "tablebasic";
    public static final String TABLE_CELL_STYLING      = "tablecellstyling";
    public static final String TABLE_COLUMN_ALIGNMENT  = "tablecolumnalignment";
    public static final String TABLE_COLUMN_COMPARATOR = "tablecolumncomparator";
    public static final String TABLE_COLUMN_PINNING    = "tablecolumnpinning";
    public static final String TABLE_DYNAMIC_STYLING   = "tabledynamicstyling";
    public static final String TABLE_EDIT_DATA         = "tableeditdata";
    public static final String TABLE_FILTERING         = "tablefiltering";
    public static final String TABLE_LAYOUT_STYLING    = "tablelayoutstyling";
    public static final String TABLE_MULTI_SELECTION   = "tablemultiselection";
    public static final String TABLE_MULTI_SORTING     = "tablemultisorting";
    public static final String TABLE_OLYMPIC_WINNERS   = "tableolympicwinners";
    public static final String TABLE_RICH_CONTENT      = "tablerichcontent";
    public static final String TABLE_ROW_STYLING       = "tablerowstyling";
    public static final String TABLE_SINGLE_SELECTION  = "tablesingleselection";
    public static final String TABLE_SORTING           = "tablesorting";
    public static final String TABLE_SORT_ORDER        = "tablesortorder";
    public static final String TITLE_EDITOR            = "titleeditor";

    // Terminal routes
    public static final String SERVER_LOGS            = "serverlogs";
    public static final String TERMINAL_THEME_PICKER  = "terminalthemepicker";
    public static final String TERMINAL               = "terminal";

    // Text-Area routes
    public static final String TEXT_AREA                 = "textarea";
    public static final String TEXT_AREA_PREDICTED_TEXT  = "textareapredictedtext";
    public static final String TEXT_AREA_STATES          = "textareastates";
    public static final String TEXT_AREA_VALIDATION      = "textareavalidation";
    public static final String TEXT_AREA_WRAP            = "textareawrap";

    //Toast
    public static final String TOAST_COOKIES   = "toastcookies";
    public static final String TOAST_PLACEMENT = "toastplacement";
    public static final String TOAST_THEME     = "toasttheme";
    public static final String TOAST_VIEW    = "toastview";

    // Toolbar routes
    public static final String TOOLBAR_COMPACT      = "toolbarcompact";
    public static final String TOOLBAR_PROGRESS_BAR = "toolbarprogressbar";
    public static final String TOOLBAR_SLOTS        = "toolbarslots";
    public static final String TOOLBAR_THEMES       = "toolbarthemes";

    // Tree routes
    public static final String TREE_SELECTION = "treeselection";
    public static final String TREE_ICONS = "treeicons";
    public static final String TREE_LAZY_LOAD = "treelazyload";
    public static final String TREE_VIEW = "treeview";
    public static final String TREE_MODIFY = "treemodify";


    // Other routes
    public static final String HELLO_WORLD = "helloworld";


    public static String getFullUrl(String route, int port) {
        return String.format("http://localhost:%d/%s", port, route);
    }

}