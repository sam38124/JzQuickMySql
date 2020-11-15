(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'JzSqlHelper'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'JzSqlHelper'.");
    }root.JzSqlHelper = factory(typeof JzSqlHelper === 'undefined' ? {} : JzSqlHelper, kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var Platform_instance = null;
}));

//# sourceMappingURL=JzSqlHelper.js.map
