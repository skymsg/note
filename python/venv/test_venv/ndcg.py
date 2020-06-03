import numpy as np

def dcg_at_k(r,k):
    r = np.asfarray(r)[:k]
    if r.size:
       return np.sum( np.subtract(np.power(2,r),1)/np.log2(np.arange(2,r.size+2)))
    return 0.
def ndcg_at_k(r,k):
    idcg = dcg_at_k( sorted(r,reverse=True),k)
    if not idcg:
        return 0.
    dcg = dcg_at_k(r,k)
    return dcg/idcg

if __name__ == '__main__':
    r = [2,1,4,3,1]
    print(ndcg_at_k(r,4))
