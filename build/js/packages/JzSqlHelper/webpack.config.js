var config = {
  mode: 'production',
  resolve: {
    modules: [
      "node_modules"
    ]
  },
  plugins: [],
  module: {
    rules: []
  }
};

// entry
config.entry = {
    main: ["/Users/jianzhi.wang/Desktop/ktor_framework/JzSqlHelper/build/js/packages/JzSqlHelper/kotlin-dce/JzSqlHelper.js"]
};

config.output = {
    path: "/Users/jianzhi.wang/Desktop/ktor_framework/JzSqlHelper/build/distributions",
    filename: (chunkData) => {
        return chunkData.chunk.name === 'main'
            ? "JzSqlHelper.js"
            : "JzSqlHelper-[name].js";
    },
    library: "JzSqlHelper",
    libraryTarget: "umd",
};

// resolve modules
config.resolve.modules.unshift("/Users/jianzhi.wang/Desktop/ktor_framework/JzSqlHelper/build/js/packages/JzSqlHelper/kotlin-dce")

// source maps
config.module.rules.push({
        test: /\.js$/,
        use: ["kotlin-source-map-loader"],
        enforce: "pre"
});
config.devtool = 'source-map';

// save evaluated config file
var util = require('util');
var fs = require("fs");
var evaluatedConfig = util.inspect(config, {showHidden: false, depth: null, compact: false});
fs.writeFile("/Users/jianzhi.wang/Desktop/ktor_framework/JzSqlHelper/build/reports/webpack/JzSqlHelper/webpack.config.evaluated.js", evaluatedConfig, function (err) {});

// Report progress to console
// noinspection JSUnnecessarySemicolon
;(function(config) {
    const webpack = require('webpack');
    const handler = (percentage, message, ...args) => {
        let p = percentage * 100;
        let msg = `${Math.trunc(p / 10)}${Math.trunc(p % 10)}% ${message} ${args.join(' ')}`;
        msg = msg.replace(new RegExp("/Users/jianzhi.wang/Desktop/ktor_framework/JzSqlHelper/build/js", 'g'), '');;
        console.log(msg);
    };

    config.plugins.push(new webpack.ProgressPlugin(handler))
})(config);
module.exports = config
