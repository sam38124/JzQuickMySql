{
  mode: 'production',
  resolve: {
    modules: [
      '/Users/jianzhi.wang/Desktop/ktor_framework/JzSqlHelper/build/js/packages/JzSqlHelper/kotlin-dce',
      'node_modules'
    ]
  },
  plugins: [],
  module: {
    rules: [
      {
        test: /\.js$/,
        use: [
          'kotlin-source-map-loader'
        ],
        enforce: 'pre'
      }
    ]
  },
  entry: {
    main: [
      '/Users/jianzhi.wang/Desktop/ktor_framework/JzSqlHelper/build/js/packages/JzSqlHelper/kotlin-dce/JzSqlHelper.js'
    ]
  },
  output: {
    path: '/Users/jianzhi.wang/Desktop/ktor_framework/JzSqlHelper/build/distributions',
    filename: [Function: filename],
    library: 'JzSqlHelper',
    libraryTarget: 'umd'
  },
  devtool: 'source-map'
}