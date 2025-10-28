<script setup>
import { Edit, Delete } from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { ref, computed, onMounted } from "vue";
//分页条数据模型
const pageNum = ref(1); //当前页
const pageSize = ref(3); //每页条数
// 当前页码

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size;
};
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num;
};
// 首页轮播图列表数据
const indexSliderList = ref([]);
const displayIndexSlider = computed(() => {
  if (indexSliderList.value != null) {
    const startIndex = (pageNum.value - 1) * pageSize.value;
    const endIndex = startIndex + pageSize.value;
    return indexSliderList.value.slice(startIndex, endIndex);
  } else {
    return (indexSliderList.value = []);
  }
});
import {
  getIndexSliderListService,
  addIndexSliderService,
  deleteIndexSliderService,
  getShopSliderListService,
  deleteShopSliderService,
  addShopSliderService,
} from "@/api/index";
//导入token
import { useTokenStore } from "@/stores/token.js";
const tokenStore = useTokenStore();
//获取首页轮播图列表
const getIndexSliderList = async () => {
  let result = await getIndexSliderListService();
  if (result.code === 1) {
    indexSliderList.value = result.data;
  }
};
getIndexSliderList();
//删除首页轮播图
const deleteIndexSlider = async (id) => {
  ElMessageBox.confirm("确定删除吗？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      let result = await deleteIndexSliderService(id);
      if (result.code === 1) {
        ElMessage.success("删除成功");
        getIndexSliderList();
      } else {
        ElMessage.error("删除失败");
      }
    })
    .catch(() => {
      ElMessage.info("取消删除");
    });
};
//首页轮播图上传成功的回调函数
const uploadIndexSliderSuccess = async (result) => {
  console.log(result.data);
  let result1 = await addIndexSliderService(result.data);

  if (result1.code === 1) {
    ElMessage.success("添加成功");
    getIndexSliderList();
  } else {
    ElMessage.error("添加失败");
  }
};

// 选择tab
const activeIndex = ref("1");
const handleSelect = (index) => {
  activeIndex.value = index;
};
// 商品页轮播图列表数据
const shopSliderList = ref([]);
const displayShopSlider = computed(() => {
  if (shopSliderList.value != null) {
    const startIndex = (pageNum.value - 1) * pageSize.value;
    const endIndex = startIndex + pageSize.value;
    return shopSliderList.value.slice(startIndex, endIndex);
  } else {
    return (shopSliderList.value = []);
  }
});
// 获取商品页轮播图列表
const getShopSliderList = async () => {
  let result = await getShopSliderListService();
  if (result.code === 1) {
    shopSliderList.value = result.data;
  }
};
getShopSliderList();
// 删除商品页轮播图
const deleteShopSlider = async (id) => {
  ElMessageBox.confirm("确定删除吗？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      let result = await deleteShopSliderService(id);
      if (result.code === 1) {
        ElMessage.success("删除成功");
        getShopSliderList();
      } else {
        ElMessage.error("删除失败");
      }
    })
    .catch(() => {
      ElMessage.info("取消删除");
    });
};
//商品页轮播图上传成功的回调函数
const uploadShopSliderSuccess = async (result) => {
  console.log(result.data);
  let result1 = await addShopSliderService(result.data);

  if (result1.code === 1) {
    ElMessage.success("添加成功");
    getShopSliderList();
  } else {
    ElMessage.error("添加失败");
  }
};
</script>
<template>
  <el-card class="page-container" v-if="activeIndex == 1">
    <template #header>
      <div class="header">
        <div class="header-left">
          <el-menu
            :default-active="activeIndex"
            mode="horizontal"
            :ellipsis="false"
            @select="handleSelect"
          >
            <el-menu-item index="1">首页轮播图管理</el-menu-item>
            <el-menu-item index="2">商品页轮播图管理</el-menu-item>
            <!-- <el-menu-item index="3">商品品牌</el-menu-item> -->
          </el-menu>
        </div>
        <div class="extra">
          <el-upload
            class="avatar-uploader"
            :auto-upload="true"
            action="/api/admin/user/upload"
            name="file"
            :headers="{ Authorization: tokenStore.token }"
            :on-success="uploadIndexSliderSuccess"
            :show-file-list="false"
          >
            <el-popover
              placement="left"
              :width="200"
              trigger="hover"
              content="首页轮播图请上传1920x800 像素的图片"
            >
              <template #reference>
                <el-button type="primary">添加首页轮播图</el-button></template
              >
            </el-popover>
          </el-upload>
        </div>
      </div>
    </template>

    <el-table :data="displayIndexSlider">
      <el-table-column label="ID" prop="id" width="100"></el-table-column>
      <el-table-column label="图片" prop="url">
        <template #default="scope">
          <img :src="scope.row.url" style="width: 300px; margin-left: 50px" />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="deleteIndexSlider(row.id)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 分页条 -->
    <el-pagination
      v-if="indexSliderList.length > 0"
      v-model:current-page="pageNum"
      v-model:page-size="pageSize"
      layout=" total, prev, pager, next"
      background
      :total="indexSliderList.length"
      @size-change="onSizeChange"
      @current-change="onCurrentChange"
      style="margin-top: 20px; justify-content: flex-end"
    />
  </el-card>

  <!-- 商品页轮播图管理 -->
  <el-card class="page-container" v-if="activeIndex == 2">
    <template #header>
      <div class="header">
        <div class="header-left">
          <el-menu
            :default-active="activeIndex"
            mode="horizontal"
            :ellipsis="false"
            @select="handleSelect"
          >
            <el-menu-item index="1">首页轮播图管理</el-menu-item>
            <el-menu-item index="2">商品页轮播图管理</el-menu-item>
            <!-- <el-menu-item index="3">商品品牌</el-menu-item> -->
          </el-menu>
        </div>
        <div class="extra">
          <el-upload
            class="avatar-uploader"
            :auto-upload="true"
            action="/api/admin/user/upload"
            name="file"
            :headers="{ Authorization: tokenStore.token }"
            :on-success="uploadShopSliderSuccess"
            :show-file-list="false"
          >
            <el-popover
              placement="left"
              :width="200"
              trigger="hover"
              content="商品页轮播图请上传1200x500 像素的图片"
            >
              <template #reference>
                <el-button type="primary">添加商品页轮播图</el-button></template
              >
            </el-popover>
          </el-upload>
        </div>
      </div>
    </template>

    <el-table :data="displayShopSlider">
      <el-table-column label="ID" prop="id" width="100"></el-table-column>
      <el-table-column label="图片" prop="url">
        <template #default="scope">
          <img :src="scope.row.url" style="width: 300px; margin-left: 50px" />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="deleteShopSlider(row.id)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 分页条 -->
    <el-pagination
      v-if="shopSliderList.length > 0"
      v-model:current-page="pageNum"
      v-model:page-size="pageSize"
      layout=" total, prev, pager, next"
      background
      :total="shopSliderList.length"
      @size-change="onSizeChange"
      @current-change="onCurrentChange"
      style="margin-top: 20px; justify-content: flex-end"
    />
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
</style>