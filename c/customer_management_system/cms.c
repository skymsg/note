#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct customer{
    int id;
    char name[10];
    char gender[10];
    char product[10];
    int price;
    int amount;
    int payment;
    struct customer *next_customer;
}CUSTOMER;

/*
 * 申请一个新节点的内存
 */
CUSTOMER* new_customer(){
    CUSTOMER* p =  (CUSTOMER*) malloc(sizeof(CUSTOMER));
    p->payment = -1;
    return p;
}

/**
 * 打印指定会员的信息
 */
void print_customer(CUSTOMER* customer){
     printf("id:%d name:%s gender:%s product:%s price:%d amount:%d",
            (customer->id),
            (customer->name),
            (customer->gender),
            (customer->product),
            (customer->price),
            (customer->amount)
            );
     if(customer->payment== -1){
         printf("\n");
     }else{
         printf(" payment:%d\n",customer->payment);
     }
     return;
}

/**
 * 打印所有会员的信息
 */
void print_customer_list(CUSTOMER* list_head){
    CUSTOMER* p_cur = list_head;
    //往后遍历,直到 找到一个id等于新节点id的节点 或者 遍历到末尾
    while(p_cur != NULL){
        print_customer(p_cur);
        p_cur = p_cur->next_customer;
    }
     return;
}
/**
 * 计算所有会员的实付价
 */
void calculate_all_customer_payment(CUSTOMER* list_head){
    CUSTOMER* p_cur = list_head;
    //往后遍历,直到遍历到末尾
    while(p_cur != NULL){
        p_cur->payment =  p_cur->price * p_cur->amount;
        p_cur = p_cur->next_customer;
    }
     return;
}
/**
 * 计算所有会员的实付价总和
 **/
int sum_all_customer_payment(CUSTOMER* list_head){
    int sum=0;
    CUSTOMER* p_cur = list_head;
    //往后遍历,直到遍历到末尾
    while(p_cur != NULL){
        if(p_cur->payment == -1){
            //该节点之前还没有计算过实付价
            p_cur->payment =  p_cur->price * p_cur->amount;
        }
        sum += p_cur->payment;
        p_cur = p_cur->next_customer;
    }
     return sum;
}


/*
 * 根据id查找会员信息
 * 参数: list_head 链表头结点
 * 参数: id 要查找的结点id
 * 返回值: 从小到大插入之后的链表的头结点
 */
CUSTOMER* find_customer_by_id(CUSTOMER* list_head,int id){
    CUSTOMER* p_cur = list_head;
    //往后遍历,直到 找到一个id等于新节点id的节点 或者 遍历到末尾
    while(p_cur != NULL && p_cur->id != id){
        p_cur = p_cur->next_customer;
    }
    return p_cur;
}

int customer_cmp(const void * o1,const void * o2){
   int o1_pay = (*(CUSTOMER**)o1)->payment;
   int o2_pay = (*(CUSTOMER**)o2)->payment;
    return o2_pay - o1_pay;
}

CUSTOMER** get_sorted_arr(CUSTOMER* list_head){
    static CUSTOMER* arr[100];
    memset(arr,0,sizeof(CUSTOMER*)*100);
    int count=0;
    CUSTOMER* p_cur = list_head;
    //往后遍历,直到 找到一个id等于新节点id的节点 或者 遍历到末尾
    while(p_cur != NULL){
        if(p_cur->payment == -1){
            //该节点之前还没有计算过实付价
            p_cur->payment =  p_cur->price * p_cur->amount;
        }
        arr[count] = p_cur;
        p_cur = p_cur->next_customer;
        count++;
    }
    qsort(arr,count,sizeof(CUSTOMER*),customer_cmp);
    for(int i=0;i<count;++i){
        print_customer(arr[i]);
    }
    return arr;
}

/*
 * 插入一个新节点
 * 参数: list_head 链表头结点
 * 参数: new_node 要插入的结点
 * 返回值: 从小到大插入之后的链表的头结点
 */
CUSTOMER* add_customer(CUSTOMER*  list_head,CUSTOMER* new_node){
    CUSTOMER* p_pre = NULL;
    CUSTOMER* p_cur = list_head;
    //往后遍历,直到 找到一个id小于新节点id的节点 或者 遍历到末尾
    while(p_cur != NULL && p_cur->id < new_node->id){
        p_pre = p_cur;
        p_cur = p_cur->next_customer;
    }

    if(p_pre == NULL){
        /**
         * 前驱节点p_pre为NULL,说明当前节点p_cur为头节点
         * 新的节点成为了新的头节点
         */
        new_node->next_customer = p_cur;
        return new_node;
    }else{
        //把new_node 插入到p_pre 和 p_cur中间
        new_node->next_customer = p_cur;
        p_pre->next_customer = new_node;
        return list_head;
    }
}

/*
 * 根据name删除会员信息
 * 参数: list_head 链表头结点
 * 参数: name 要删除的结点的name
 * 返回值: 删除该节点之后的链表的头结点
 */

CUSTOMER* delete_customer_by_name(CUSTOMER* list_head,char* name){
    CUSTOMER* p_pre = NULL;
    CUSTOMER* p_cur = list_head;
    //往后遍历,直到 找到一个name等于参数name的节点 或者 遍历到末尾
    while(p_cur != NULL && strcmp(p_cur->name,name) != 0){
        p_pre = p_cur;
        p_cur = p_cur->next_customer;
    }

    if(p_cur == NULL){
        printf("删除失败,不存在name为:%s的节点\n",name);
    }else{
        printf("name为:%s的节点删除成功\n",name);
        printf("该会员的信息为:\n");
        print_customer(p_cur);
        if(p_pre == NULL){//p_cur的前驱结点为NULL,则p_cur为头结点
            list_head = p_cur->next_customer; 
        }else{
            p_pre->next_customer = p_cur->next_customer;
        }
        free(p_cur);//回收该节点的内存
    }
    return list_head;
}

/**
 *打印功能菜单
 */
int menu(){
    printf("***********************请输入对应功能的编号******************\n");
    printf("1: 输入一个会员号,查询该会员的信息并输出,若不存在则显示没找到\n");
    printf("2: 输入一个新会员的信息,按会员号顺序将信息插入后输出\n");
    printf("3: 输入一个已经存在的会员姓名信息,删除该会员的信息后输出\n");
    printf("4: 求每个会员的实付价(实付价=价格*数量)\n");
    printf("5: 求每个会员实付价总和并输出\n");
    printf("6: 找出实付价从大到小的第二名和第七名的信息并输出\n");
    printf("7: 退出程序\n");
    int choice;
    scanf("%d",&choice);
    return choice;
}

/**
 *加载数据
 */
CUSTOMER *load_data(char* path){
    printf("开始加载数据...\n");
    FILE *input_fp = fopen(path,"r");
    if(input_fp == NULL){
        printf("打开数据文件失败!文件不存在或者没有权限\n");
        exit(-1);
    }
    char cloumns[200];
    fgets(cloumns,200,input_fp);
    feof(input_fp);
    printf("数据文件列名:\n%s数据列表:\n",cloumns);
    CUSTOMER *list_head = NULL;
    //循环读取数据直到文件末尾
    while(1){
        CUSTOMER *new_node = new_customer();
        //将文件中的数据读取到我们申请的内存中
        int err=fscanf(input_fp,"%d %s %s %s %d %d\n",
                &(new_node->id),
                (new_node->name),
                (new_node->gender),
                (new_node->product),
                &(new_node->price),
                &(new_node->amount)
                );
        if(err==EOF){
            printf("读取数据完毕,所有会员的信息为:\n");
            print_customer_list(list_head);
            break;
        }
        list_head = add_customer(list_head,new_node);
    }
    return list_head;
}

/**
 * 主函数
 */
int main(int argc,char** argv){

    if(argc<2){
            printf("未指定数据文件路\n用法: ./cms  file_path\n");
            exit(-1);
    }
    //获取文件路径
    char* path = argv[1];
    //加载数据
    CUSTOMER* customer_list = load_data(path);

    int choice;
    while(choice!=7){
      choice  =  menu(); 
      switch(choice){
            case 1:
                {
                    printf("请输入会员id:");
                    int id;
                    scanf("%d",&id);
                    CUSTOMER* finded_customer = find_customer_by_id(customer_list,id);
                    if(finded_customer == NULL){
                        printf("会员id:%d不存在\n",id);
                    }else{
                        printf("id为%d的会员信息为:\n",id);
                        print_customer(finded_customer);
                    }
                    break;
                }
            case 2:
                    printf("请输入会员信息 格式为:id name gender product price amount\n");
                     CUSTOMER *new_node = new_customer();
                    //将文件中的数据读取到我们申请的内存中
                    int err=scanf("%d %s %s %s %d %d",
                            &(new_node->id),
                            (new_node->name),
                            (new_node->gender),
                            (new_node->product),
                            &(new_node->price),
                            &(new_node->amount)
                            );
                    if(err==EOF){
                        printf("输入有误!\n");
                        break;
                    }
                    customer_list = add_customer(customer_list,new_node);
                    printf("新插入的会员信息:\n");
                    print_customer(new_node);    
                    printf("所有会员信息列表:\n");
                    print_customer_list(customer_list);
                    break;
                case 3:
                    printf("请输入会员name:");
                    char name[30];
                    scanf("%s",name);
                    customer_list = delete_customer_by_name(customer_list,name);
                    printf("所有会员信息列表:\n");
                    print_customer_list(customer_list);
                    break;
                case 4:
                    calculate_all_customer_payment(customer_list);
                    printf("计算完毕,所有会员信息列表:\n");
                    print_customer_list(customer_list);
                    break;
                case 5:
                    {
                        int sum = sum_all_customer_payment(customer_list);
                        printf("实付价总和为:%d\n",sum);
                        break;
                    }
                case 6:
                    {
                        CUSTOMER** p = get_sorted_arr(customer_list);
                        printf("实付价从大到小第二名:");
                        print_customer(p[1]);
                        printf("实付价从大到小第七名:");
                        print_customer(p[6]);
                    }
                    break;
                case 7:
                    printf("正在退出 ...\n");
                    break;
                default:
                printf("未知的操作,请重新输入\n");
                break;
      }
    }
    return 0;
}
