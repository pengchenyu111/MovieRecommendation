module.exports = {
  configureWebpack: {
    devServer: {
      port: 9000
    },
    resolve: {
      alias: {
        'assets': '@/assets',
        'api': '@/api',
        'common': '@/common',
        'components': '@/components',
        'views': '@/views',
      }
    }
  },
  transpileDependencies: [
    'vuetify'
  ]
}
