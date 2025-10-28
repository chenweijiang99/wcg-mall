<script setup>
import { Edit, Delete } from "@element-plus/icons-vue";

import { ref, computed, onMounted } from "vue";

//分页条数据模型
const pageNum = ref(1); //当前页
const pageSize = ref(5); //每页条数
// 当前页码

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size;
};
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num;
};





// 选择tab
const activeIndex = ref("1");
const handleSelect = (index) => {
  activeIndex.value = index;
};

const orders = ref([]);
const orderDetail = ref([]);

import { getOrderList, getOrderDetailList,OrderShipping } from "@/api/order.js";
import { ElMessage } from "element-plus";
//获取订单列表
const gerOrderList = async () => {
  let result = await getOrderList();
  orders.value = result.data;
};
//获取订单详情列表
const OrderDetailList = async () => {
  let result = await getOrderDetailList();
  orderDetail.value = result.data;
};
//发货
const OrderShippingButton = async (orderNumber) => {
  let result = await OrderShipping(orderNumber);
  if(result.code == 1){
    ElMessage.success(result.msg?result.msg:'发货成功');
    gerOrderList();
  }else{
    ElMessage.error(result.msg?result.msg:'发货失败');
  }
}

//取消订单
const CancelOrder = async (orderNumber) => {
  ElMessage.warning('暂未开放');
}
onMounted(() => {
  gerOrderList();
  OrderDetailList();
});
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <div class="header-left">
          <el-menu
            :default-active="activeIndex"
            mode="horizontal"
            :ellipsis="false"
            @select="handleSelect"
          >
            <el-menu-item index="1">待付款</el-menu-item>
            <el-menu-item index="2">待发货</el-menu-item>
            <el-menu-item index="3">已发货</el-menu-item>
            <el-menu-item index="4">已完成</el-menu-item>
            <el-menu-item index="5">已取消</el-menu-item>
          </el-menu>
        </div>
      </div>
    </template>

    <!-- 待付款订单列表 -->
    <div v-if="activeIndex === '1'">
      <el-table
        :data="orders.filter((order) => order.status === 1)"
        style="width: 100%"
        height="600"
        stripe
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
      >
        <el-table-column type="expand">
          <template #default="props">
            <div style="display: flex; flex-wrap: wrap">
              <div style="flex: 0 0 20%; max-width: 20%; margin-top: 2rem">
                <p m="t-0 b-2">收货人：{{ props.row.consignee }}</p>
                <p m="t-0 b-2">收货地址：{{ props.row.consigneeAddress }}</p>
                <p m="t-0 b-2">收货人电话：{{ props.row.consigneePhone }}</p>
                <p m="t-0 b-2">下单时间：{{ props.row.orderTime }}</p>
              </div>
              <div style="flex: 0 0 20%; max-width: 20%; margin-top: 2rem">
                <p m="t-0 b-2">
                  支付方式：{{ props.row.payMethod === 1 ? "支付宝" : "微信" }}
                </p>
                <p m="t-0 b-2">
                  支付状态：{{
                    props.row.payStatus === 1 ? "已支付" : "未支付"
                  }}
                </p>
                <p m="t-0 b-2">订单金额：{{ props.row.amount }}</p>
                <p m="t-0 b-2">支付时间：{{ props.row.checkoutTime }}</p>
              </div>
              <div style="flex: 0 0 60%; max-width: 60%">
                <h3>商品列表</h3>
                <el-table
                  border
                  :data="
                    orderDetail.filter(
                      (orderDetail) =>
                        orderDetail.orderNumber == props.row.orderNumber
                    )
                  "
                  :header-cell-style="{ 'text-align': 'center' }"
                  :cell-style="{ 'text-align': 'center' }"
                  width="100%"
                >
                  <el-table-column
                    label="商品名称"
                    prop="productName"
                  ></el-table-column>
                  <el-table-column label="商品图片" prop="productImage">
                    <template #default="scope">
                      <el-image
                        :src="scope.row.productImage"
                        style="width: 30%"
                      ></el-image>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="商品价格"
                    prop="productPrice"
                  ></el-table-column>
                  <el-table-column label="商品数量" prop="productNumber">
                  </el-table-column>
                </el-table>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="id" prop="id"></el-table-column>
        <el-table-column label="订单号" prop="orderNumber"></el-table-column>
        <el-table-column label="邮箱" prop="email"></el-table-column>
        <el-table-column label="订单时间" prop="orderTime"></el-table-column>
        <template #empty>
          <el-empty description="没有数据" />
        </template>
      </el-table>

      <!-- 分页条 -->
      <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        layout=" total, prev, pager, next"
        background
        :total="orders.filter((order) => order.status === 1).length"
        @size-change="onSizeChange"
        @current-change="onCurrentChange"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </div>

    <!-- 待发货订单列表 -->
    <div v-if="activeIndex === '2'">
      <el-table
        :data="orders.filter((order) => order.status === 2)"
        style="width: 100%"
        stripe
        highlight-current-row
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
      >
        <el-table-column type="expand">
          <template #default="props">
            <div style="display: flex; flex-wrap: wrap">
              <div style="flex: 0 0 20%; max-width: 20%; margin-top: 2rem">
                <p m="t-0 b-2">收货人：{{ props.row.consignee }}</p>
                <p m="t-0 b-2">收货地址：{{ props.row.consigneeAddress }}</p>
                <p m="t-0 b-2">收货人电话：{{ props.row.consigneePhone }}</p>
                <p m="t-0 b-2">下单时间：{{ props.row.orderTime }}</p>
              </div>
              <div style="flex: 0 0 20%; max-width: 20%; margin-top: 2rem">
                <p m="t-0 b-2">
                  支付方式：{{ props.row.payMethod === 1 ? "支付宝" : "微信" }}
                </p>
                <p m="t-0 b-2">
                  支付状态：{{
                    props.row.payStatus === 1 ? "已支付" : "未支付"
                  }}
                </p>
                <p m="t-0 b-2">订单金额：{{ props.row.amount }}</p>
                <p m="t-0 b-2">支付时间：{{ props.row.checkoutTime }}</p>
              </div>
              <div style="flex: 0 0 60%; max-width: 60%">
                <h3>商品列表</h3>
                <el-table
                  border
                  :data="
                    orderDetail.filter(
                      (orderDetail) =>
                        orderDetail.orderNumber == props.row.orderNumber
                    )
                  "
                  :header-cell-style="{ 'text-align': 'center' }"
                  :cell-style="{ 'text-align': 'center' }"
                  width="100%"
                >
                  <el-table-column
                    label="商品名称"
                    prop="productName"
                  ></el-table-column>
                  <el-table-column label="商品图片" prop="productImage">
                    <template #default="scope">
                      <el-image
                        :src="scope.row.productImage"
                        style="width: 20%"
                      ></el-image>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="商品价格"
                    prop="productPrice"
                  ></el-table-column>
                  <el-table-column
                    label="商品数量"
                    prop="productNumber"
                  ></el-table-column>
                </el-table>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="id" prop="id"></el-table-column>
        <el-table-column label="订单号" prop="orderNumber"></el-table-column>
        <el-table-column label="邮箱" prop="email"></el-table-column>
        <el-table-column label="订单时间" prop="orderTime"></el-table-column>
        <el-table-column label="操作" prop="status">
          <template #default="scope">
            <el-button
              plain
              type="danger"
              @click="CancelOrder(scope.row.orderNumber)"
              >取消订单</el-button
            >
            <el-button
              plain
              type="success"
              @click="OrderShippingButton(scope.row.orderNumber)"
              >发货</el-button
            >
          </template>
        </el-table-column>
        <template #empty>
          <el-empty description="没有数据" />
        </template>
      </el-table>

      <!-- 分页条 -->
      <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        layout=" total, prev, pager, next"
        background
        :total="orders.filter((order) => order.status === 2).length"
        @size-change="onSizeChange"
        @current-change="onCurrentChange"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </div>

    <!-- 已发货订单列表 -->
    <div v-if="activeIndex === '3'">
      <el-table
        :data="orders.filter((order) => order.status === 3)"
        style="width: 100%"
        stripe
        highlight-current-row
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
      >
        <el-table-column type="expand">
          <template #default="props">
            <div style="display: flex; flex-wrap: wrap">
              <div style="flex: 0 0 20%; max-width: 20%; margin-top: 2rem">
                <p m="t-0 b-2">收货人：{{ props.row.consignee }}</p>
                <p m="t-0 b-2">收货地址：{{ props.row.consigneeAddress }}</p>
                <p m="t-0 b-2">收货人电话：{{ props.row.consigneePhone }}</p>
                <p m="t-0 b-2">下单时间：{{ props.row.orderTime }}</p>
              </div>
              <div style="flex: 0 0 20%; max-width: 20%; margin-top: 2rem">
                <p m="t-0 b-2">
                  支付方式：{{ props.row.payMethod === 1 ? "支付宝" : "微信" }}
                </p>
                <p m="t-0 b-2">
                  支付状态：{{
                    props.row.payStatus === 1 ? "已支付" : "未支付"
                  }}
                </p>
                <p m="t-0 b-2">订单金额：{{ props.row.amount }}</p>
                <p m="t-0 b-2">支付时间：{{ props.row.checkoutTime }}</p>
              </div>
              <div style="flex: 0 0 60%; max-width: 60%">
                <h3>商品列表</h3>
                <el-table
                  border
                  :data="
                    orderDetail.filter(
                      (orderDetail) =>
                        orderDetail.orderNumber == props.row.orderNumber
                    )
                  "
                  :header-cell-style="{ 'text-align': 'center' }"
                  :cell-style="{ 'text-align': 'center' }"
                  width="100%"
                >
                  <el-table-column
                    label="商品名称"
                    prop="productName"
                  ></el-table-column>
                  <el-table-column label="商品图片" prop="productImage">
                    <template #default="scope">
                      <el-image
                        :src="scope.row.productImage"
                        style="width: 20%"
                      ></el-image>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="商品价格"
                    prop="productPrice"
                  ></el-table-column>
                </el-table>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="id" prop="id"></el-table-column>
        <el-table-column label="订单号" prop="orderNumber"></el-table-column>
        <el-table-column label="邮箱" prop="email"></el-table-column>
        <el-table-column label="订单时间" prop="orderTime"></el-table-column>

        <template #empty>
          <el-empty description="没有数据" />
        </template>
      </el-table>

      <!-- 分页条 -->
      <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        layout=" total, prev, pager, next"
        background
        :total="orders.filter((order) => order.status === 3).length"
        @size-change="onSizeChange"
        @current-change="onCurrentChange"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </div>

    <!-- 已完成订单列表 -->
    <div v-if="activeIndex == '4'">
      <el-table
        :data="orders.filter((order) => order.status === 4)"
        style="width: 100%"
        stripe
        highlight-current-row
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
      >
        <el-table-column type="expand">
          <template #default="props">
            <div style="display: flex; flex-wrap: wrap">
              <div style="flex: 0 0 20%; max-width: 20%; margin-top: 2rem">
                <p m="t-0 b-2">收货人：{{ props.row.consignee }}</p>
                <p m="t-0 b-2">收货地址：{{ props.row.consigneeAddress }}</p>
                <p m="t-0 b-2">收货人电话：{{ props.row.consigneePhone }}</p>
                <p m="t-0 b-2">下单时间：{{ props.row.orderTime }}</p>
              </div>
              <div style="flex: 0 0 20%; max-width: 20%; margin-top: 2rem">
                <p m="t-0 b-2">
                  支付方式：{{ props.row.payMethod === 1 ? "支付宝" : "微信" }}
                </p>
                <p m="t-0 b-2">
                  支付状态：{{
                    props.row.payStatus === 1 ? "已支付" : "未支付"
                  }}
                </p>
                <p m="t-0 b-2">订单金额：{{ props.row.amount }}</p>
                <p m="t-0 b-2">支付时间：{{ props.row.checkoutTime }}</p>
              </div>
              <div style="flex: 0 0 60%; max-width: 60%">
                <h3>商品列表</h3>
                <el-table
                  border
                  :data="
                    orderDetail.filter(
                      (orderDetail) =>
                        orderDetail.orderNumber == props.row.orderNumber
                    )
                  "
                  :header-cell-style="{ 'text-align': 'center' }"
                  :cell-style="{ 'text-align': 'center' }"
                  width="100%"
                >
                  <el-table-column
                    label="商品名称"
                    prop="productName"
                  ></el-table-column>
                  <el-table-column label="商品图片" prop="productImage">
                    <template #default="scope">
                      <el-image
                        :src="scope.row.productImage"
                        style="width: 20%"
                      ></el-image>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="商品价格"
                    prop="productPrice"
                  ></el-table-column>
                </el-table>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="id" prop="id"></el-table-column>
        <el-table-column label="订单号" prop="orderNumber"></el-table-column>
        <el-table-column label="邮箱" prop="email"></el-table-column>
        <el-table-column label="订单时间" prop="orderTime"></el-table-column>

        <template #empty>
          <el-empty description="没有数据" />
        </template>
      </el-table>

      <!-- 分页条 -->
      <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        layout=" total, prev, pager, next"
        background
        :total="orders.filter((order) => order.status === 4).length"
        @size-change="onSizeChange"
        @current-change="onCurrentChange"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </div>

    <!-- 已取消订单列表 -->
    <div v-if="activeIndex == '5'">
      <el-table
        :data="orders.filter((order) => order.status === 5)"
        style="width: 100%"
        stripe
        highlight-current-row
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
      >
        <el-table-column type="expand">
          <template #default="props">
            <div style="display: flex; flex-wrap: wrap">
              <div style="flex: 0 0 20%; max-width: 20%; margin-top: 2rem">
                <p m="t-0 b-2">收货人：{{ props.row.consignee }}</p>
                <p m="t-0 b-2">收货地址：{{ props.row.consigneeAddress }}</p>
                <p m="t-0 b-2">收货人电话：{{ props.row.consigneePhone }}</p>
                <p m="t-0 b-2">下单时间：{{ props.row.orderTime }}</p>
              </div>
              <div style="flex: 0 0 20%; max-width: 20%; margin-top: 2rem">
                <p m="t-0 b-2">
                  支付方式：{{ props.row.payMethod === 1 ? "支付宝" : "微信" }}
                </p>
                <p m="t-0 b-2">
                  支付状态：{{
                    props.row.payStatus === 1 ? "已支付" : "未支付"
                  }}
                </p>
                <p m="t-0 b-2">订单金额：{{ props.row.amount }}</p>
                <p m="t-0 b-2">支付时间：{{ props.row.checkoutTime }}</p>
              </div>
              <div style="flex: 0 0 60%; max-width: 60%">
                <h3>商品列表</h3>
                <el-table
                  border
                  :data="
                    orderDetail.filter(
                      (orderDetail) =>
                        orderDetail.orderNumber == props.row.orderNumber
                    )
                  "
                  :header-cell-style="{ 'text-align': 'center' }"
                  :cell-style="{ 'text-align': 'center' }"
                  width="100%"
                >
                  <el-table-column
                    label="商品名称"
                    prop="productName"
                  ></el-table-column>
                  <el-table-column label="商品图片" prop="productImage">
                    <template #default="scope">
                      <el-image
                        :src="scope.row.productImage"
                        style="width: 20%"
                      ></el-image>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="商品价格"
                    prop="productPrice"
                  ></el-table-column>
                </el-table>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="id" prop="id"></el-table-column>
        <el-table-column label="订单号" prop="orderNumber"></el-table-column>

        <el-table-column label="邮箱" prop="email"></el-table-column>
        <el-table-column label="订单时间" prop="orderTime"></el-table-column>

        <template #empty>
          <el-empty description="没有数据" />
        </template>
      </el-table>

      <!-- 分页条 -->
      <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        layout=" total, prev, pager, next"
        background
        :total="orders.filter((order) => order.status === 5).length"
        @size-change="onSizeChange"
        @current-change="onCurrentChange"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </div>
  </el-card>
</template>
<style lang="scss" scoped>
.hidden {
  display: none;
}
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  .el-select {
    width: 150px;
  }
}

/* 抽屉样式 */
.avatar-uploader {
  :deep() {
    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }

    .el-upload {
      border: 1px dashed var(--el-border-color);
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);
    }

    .el-upload:hover {
      border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      text-align: center;
    }
  }
}
.editor {
  width: 100%;
  :deep(.ql-editor) {
    min-height: 200px;
  }
}
</style>