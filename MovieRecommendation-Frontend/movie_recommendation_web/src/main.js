import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'element-ui/lib/theme-chalk/base.css';
import vuetify from './plugins/vuetify';

// 导入vue-echarts插件
import ECharts from "vue-echarts";
import {use} from 'echarts/core'
// 按需导入渲染器
import {
  CanvasRenderer
} from 'echarts/renderers'
// 按需导入echarts的图形类型
import {
  BarChart,
  PieChart,
  PictorialBarChart
} from 'echarts/charts'
// 按需导入工具部分
import {
  GridComponent,
  TooltipComponent,
  TitleComponent,
  LegendComponent,
  //ToolboxComponent,
  //MarkPointComponent,
  //MarkLineComponent,
  //DataZoomComponent,
  //VisualMapComponent
} from 'echarts/components';

use([
  CanvasRenderer,
  BarChart,
  PieChart,
  PictorialBarChart,
  GridComponent,
  TooltipComponent,
  TitleComponent,
  LegendComponent,
  //ToolboxComponent,
  //MarkPointComponent,
  //MarkLineComponent,
  //DataZoomComponent,
  //VisualMapComponent
]);
// 全局注册chart组件
Vue.component('VueEcharts', ECharts)

Vue.config.productionTip = false

Vue.use(ElementUI)
new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
