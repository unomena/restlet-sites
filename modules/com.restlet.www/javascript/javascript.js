/** GENERAL */
var min = 8;
var max = 18;

/**
 * Increases the font size of a full paragraph.
 * 
 * @param section
 *           The DOM element whose font size will be increased.
 */
function increaseFontSize(section) {
   var fontSize = section.css("font-size");
   if (fontSize) {
      var s = parseInt(fontSize.replace("px", ""));
   } else {
      var s = 12;
   }
   if (s != max) {
      s += 1;
   }
   section.css("font-size", s + "px");
}

/**
 * Decreases the font size of a full paragraph.
 * 
 * @param section
 *           The DOM element whose font size will be decreased.
 */
function decreaseFontSize(section) {
   var fontSize = section.css("font-size");
   if (fontSize) {
      var s = parseInt(fontSize.replace("px", ""));
   } else {
      var s = 12;
   }
   if (s != min) {
      s -= 1;
   }
   section.css("font-size", s + "px");
}

/** MENUS */
/* Timeout object used for controlling the display of current sub-menu */
var timeoutObject;
/* Timeout */
var timeoutTime = 400;
/* Name of the current menu */
var currentMenuName;

/**
 * Display the sub menus of the given menu.
 * 
 * @param selectedMenuName
 *           The name of the current selected menu.
 * @param menuName
 *           The name of the menu.
 */
function showSubmenus(selectedMenuName, menuName) {
   clearTimeout(timeoutObject);

   if (menuName != "-") {
      var $submenus = $("#submenu").children();
      $submenus.hide();

      if (menuName) {
         currentMenuName = menuName;
      }
      if (currentMenuName) {
         $("#submenu-" + currentMenuName).show();
      } else {
         if (selectedMenuName != "-") {
            $("#submenu-" + selectedMenuName).show();
         }
      }
   }
}

/**
 * Hide the sub menus of the given menu. The submenus are not hidden
 * immediately, in case the mouse points to the submenu div.
 * 
 * @param selectedMenuName
 *           The name of the current selected menu.
 * @param menuName
 *           The name of the menu.
 */
function hideSubmenus(selectedMenuName, menuName) {
   var v = currentMenuName; // by default
   if (menuName) {
      v = menuName;
   }

   if (v) {
      if (selectedMenuName != "-") {
         timeoutObject = setTimeout("showSubmenus('" + selectedMenuName
               + "', '" + selectedMenuName + "');", timeoutTime);
      } else {
         timeoutObject = setTimeout("$('#submenu-" + v
               + "').hide(); currentMenuName = null;", timeoutTime);
      }
   }
}