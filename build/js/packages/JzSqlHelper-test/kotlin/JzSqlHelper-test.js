(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'JzSqlHelper', 'kotlin-test'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('JzSqlHelper'), require('kotlin-test'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'JzSqlHelper-test'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'JzSqlHelper-test'.");
    }if (typeof JzSqlHelper === 'undefined') {
      throw new Error("Error loading module 'JzSqlHelper-test'. Its dependency 'JzSqlHelper' was not found. Please, check whether 'JzSqlHelper' is loaded prior to 'JzSqlHelper-test'.");
    }if (typeof this['kotlin-test'] === 'undefined') {
      throw new Error("Error loading module 'JzSqlHelper-test'. Its dependency 'kotlin-test' was not found. Please, check whether 'kotlin-test' is loaded prior to 'JzSqlHelper-test'.");
    }root['JzSqlHelper-test'] = factory(typeof this['JzSqlHelper-test'] === 'undefined' ? {} : this['JzSqlHelper-test'], kotlin, JzSqlHelper, this['kotlin-test']);
  }
}(this, function (_, Kotlin, $module$JzSqlHelper, $module$kotlin_test) {
  'use strict';
  var Sample = $module$JzSqlHelper.sample.Sample;
  var assertTrue = $module$kotlin_test.kotlin.test.assertTrue_ifx8ge$;
  var hello = $module$JzSqlHelper.sample.hello;
  var println = Kotlin.kotlin.io.println_s8jyv4$;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var test = $module$kotlin_test.kotlin.test.test;
  var suite = $module$kotlin_test.kotlin.test.suite;
  var contains = Kotlin.kotlin.text.contains_li3zpu$;
  function SampleTests() {
  }
  SampleTests.prototype.testMe = function () {
    assertTrue((new Sample()).checkMe() > 0);
    println(hello());
  };
  SampleTests.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'SampleTests',
    interfaces: []
  };
  function SampleTestsJS() {
  }
  SampleTestsJS.prototype.testHello = function () {
    assertTrue(contains(hello(), 'JS'));
  };
  SampleTestsJS.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'SampleTestsJS',
    interfaces: []
  };
  var package$sample = _.sample || (_.sample = {});
  package$sample.SampleTests = SampleTests;
  package$sample.SampleTestsJS = SampleTestsJS;
  suite('sample', false, function () {
    suite('SampleTests', false, function () {
      test('testMe', false, function () {
        return (new SampleTests()).testMe();
      });
    });
    suite('SampleTestsJS', false, function () {
      test('testHello', false, function () {
        return (new SampleTestsJS()).testHello();
      });
    });
  });
  Kotlin.defineModule('JzSqlHelper-test', _);
  return _;
}));

//# sourceMappingURL=JzSqlHelper-test.js.map
