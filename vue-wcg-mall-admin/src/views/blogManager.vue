<script setup>
import { Edit, Delete } from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox } from "element-plus";
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

const blogs = ref([]);

const displayBlogs = computed(() => {
  if (blogs.value != null) {
    const startIndex = (pageNum.value - 1) * pageSize.value;
    const endIndex = startIndex + pageSize.value;
    return blogs.value.slice(startIndex, endIndex);
  } else {
    return (blogs.value = []);
  }
});
import { getBlogListService, deleteBlogService } from "@/api/blog.js";
// 获取博客列表
const getBlogList = async () => {
  const res = await getBlogListService();
  blogs.value = res.data;
};
getBlogList();
// 删除博客
const deleteBlog = async (id) => {
  ElMessageBox.confirm("此操作将永久删除该博客, 是否继续?", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(async () => {
    let result = await deleteBlogService(id);
    if (result.code == 1) {
      ElMessage.success("删除成功");
      getBlogList();
    } else ElMessage.error(result.msg);
  });
};
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>文章管理</span>
      </div>
    </template>
    <!-- 文章列表 -->
    <el-table :data="displayBlogs">
      <el-table-column type="expand">
        <template #default="props">
          <div style="display: flex; flex-wrap: wrap">
            <div style="display: flex; justify-content: space-between">
              <img
                :src="props.row.image"
                alt=""
                style="width: 300px; margin-left: 50px"
              />
              <p style="margin-left: 50px; margin-right: 50px">
                {{ props.row.content }}
              </p>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="ID" prop="id" width="100"></el-table-column>
      <el-table-column
        label="文章标题"
        width="500"
        prop="title"
      ></el-table-column>
      <el-table-column label="作者" prop="author"></el-table-column>
      <el-table-column label="发表时间" prop="createTime"> </el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="deleteBlog(row.id)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 分页条 -->
    <el-pagination
      v-if="blogs.length > 0"
      v-model:current-page="pageNum"
      v-model:page-size="pageSize"
      layout=" total, prev, pager, next"
      background
      :total="blogs.length"
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