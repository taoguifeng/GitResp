<template>
  <div>
    <el-form
      ref="form"
      :model="form"
      :rules="rules"
      label-width="80px"
      class="form"
    >
      <el-form-item label-width="100px" label="套餐名称:" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>

      <el-form-item label-width="100px" label="具体配置:" prop="detail">
        <el-button size="mini" @click="handleAdd()">选择添加</el-button>
        <el-table
          highlight-current-row
          @current-change="handleCurrentChange"
          :data="form.comboDish"
          height="350"
          border
          style="width: 100%"
          :row-class-name="tableRowClassName"
        >
          <el-table-column prop="dishId" label="菜品编号" width="180">
            <template #default="scope">
              <el-input v-model="scope.row.dishId" disabled></el-input>
            </template>
          </el-table-column>

          <el-table-column prop="dishName" label="菜品名称" width="180">
            <template #default="scope">
              <el-input v-model="scope.row.dishName" disabled></el-input>
            </template>
          </el-table-column>

          <el-table-column prop="dishNum" label="菜品数量">
            <template #default="scope">
              <el-input
                v-model="scope.row.dishNum"
                @input="scope.row.update = true"
                @change="numChange"
              ></el-input>
            </template>
          </el-table-column>

          <el-table-column label="操作">
            <template #default="scope">
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)"
                >移除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>

      <el-form-item label-width="100px" label="套餐单价:" prop="price">
        <el-input
          disabled
          v-model.number="form.price"
          placeholder="请填写套餐具体配置"
        ></el-input>
      </el-form-item>

      <el-form-item label-width="100px" label="套餐分类:" prop="kind">
        <el-checkbox-group v-model="form.classificationIds">
          <el-checkbox v-for="c in cs" :label="c.id" :key="c.id">{{
            c.name
          }}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>

      <el-form-item label-width="100px" label="套餐标签:" prop="tags">
        <el-input
          type="textarea"
          :rows="3"
          placeholder="多个标签请以空格或换行分隔，如：养生菜 下酒菜 适合小孩子吃"
          v-model="form.tag"
        >
        </el-input>
      </el-form-item>

      <el-form-item label="优惠方式: " label-width="100px">
        <el-radio-group
          v-model="form.discount.type"
          size="medium"
          @change="test"
        >
          <el-radio-button border label="0">无优惠</el-radio-button>
          <el-radio-button border label="1">折扣优惠</el-radio-button>
          <el-radio-button border label="2">立减优惠</el-radio-button>
        </el-radio-group>
      </el-form-item>

      <el-form-item
        v-if="form.discount.type !== 0"
        label="优惠力度: "
        label-width="100px"
      >
        <el-input
          v-if="form.discount.type == 1"
          v-model.number="form.discount.val"
          placeholder="请输入折扣力度，输入应为一个 0 ~ 10 之间的数，例如输入 9 表示九折"
        ></el-input>
        <el-input
          v-if="form.discount.type == 2"
          v-model="form.discount.val"
          placeholder="请输入立减价格，输入应为一个整数或小数，单位为元"
        ></el-input>
        <span style="color:red; font-size=15px;"
          >注：套餐作为长期营销手段，应当长期稳定提供优惠，不应设置限制</span
        >
      </el-form-item>

      <el-form-item style="margin-left: 20px">
        <el-button type="primary" @click="handleSubmit">立即创建</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
    <el-dialog title="选择菜品" v-model="selectDialogVisible" width="50%">
      <el-table
        :data="dialogData.list"
        :row-class-name="tableRowClassNameOfDialog"
        border
        @row-click="rowClick"
      >
        <el-table-column label="菜品ID" align="center">
          <template #default="scope">{{ scope.row.id }}</template>
        </el-table-column>
        <el-table-column label="菜品名称" width="160" align="center">
          <template #default="scope">{{ scope.row.name }}</template>
        </el-table-column>
        <el-table-column label="菜品价格" width="160" align="center">
          <template #default="scope">￥{{ scope.row.price }}</template>
        </el-table-column>
        <el-table-column label="菜品类型" width="160" align="center">
          <template #default="scope">{{
            scope.row.id >= 100000 ? "套餐" : "菜品"
          }}</template>
        </el-table-column>
      </el-table>
      <div style="clear: both"></div>

      <div slot="footer" style="padding: 20px 0; text-align: right">
        <el-button @click="selectDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSelectDialogConfirm()"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { dishList, classification_list } from "../../../api/dish";
import { get_combo, createCombo, updateCombo } from "../../../api/combo";

export default {
  data() {
    return {
      loading: false,
      selectDialogVisible: false,
      dialogSelectRow: null,
      cs: ["yyds"],
      dialogData: {
        list: null,
        total: null,
        listQuery: {
          keyword: null,
          pageNum: 1,
          pageSize: 8,
        },
      },
      rules: {
        name: [
          { required: true, message: "请输入套餐名称", trigger: "blur" },
          {
            min: 1,
            max: 20,
            message: "长度为 1 到 20 个字符",
            trigger: "blur",
          },
        ],
      },
      form: {
        discount: {},
        comboDish: [],
      },
    };
  },
  methods: {
    handleAdd() {
      this.selectDialogVisible = true;
      this.dialogData.listQuery.keyword = null;
      this.getDialogList();
    },
    getDialogList() {
      dishList({
        pageNum: 1,
        pageSize: 100,
      }).then((response) => {
        console.log(response);
        this.dialogData.list = response.arrays;
        this.dialogData.total = response.total;
        this.selectDialogVisible = true;
      });
    },
    rowClick(row, event, column) {
      console.log("点击事件触发" + column, row);
      this.dialogSelectRow = row;
    },
    handleSelectDialogConfirm() {
      if (!this.dialogSelectRow) {
        this.$message({
          message: "请选择一条记录",
          type: "warning",
          duration: 1000,
        });
        return;
      }
      this.$confirm("确定选择?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.form.comboDish.unshift({
          dishId: this.dialogSelectRow.id,
          dishName: this.dialogSelectRow.name,
          dishPrice: this.dialogSelectRow.price,
        });
        this.selectDialogVisible = false;
        this.dialogSelectRow = null;

        console.log("确定", this.form);
      });
    },
    handleSubmit() {
      const submitForm = {
        ...this.form
      }
      submitForm.tags = this.form.tag == null ? [] : this.form.tag.split("\\s+");
      if (this.form.id) {
        updateCombo(submitForm).then(() => {
          this.$message.success("修改套餐成功");
        });
      } else {
        createCombo(submitForm).then(() => {
          this.$message.success("添加套餐成功");
        });
      }
    },
    getCombo(id) {
      get_combo(id).then((res) => {
        this.form = res;
      });
    },
    numChange() { 
      let list = this.form.comboDish;
      let price = 0;
      console.log(list, "lilili");
      list.forEach((item) => {
        price += item.dishPrice * item.dishNum;
      });
      this.form.price = price;
    },
  },
  created() {
    if (this.$route.query.id) {
      this.getCombo(this.$route.query.id);
    }
    classification_list().then((res) => {
      this.cs = res.arrays;
    });
  },
};
</script>

<style></style>
