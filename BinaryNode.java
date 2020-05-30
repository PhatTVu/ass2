 import java.util.*;
 class BinaryNode{
		private int pid;
		private ArrayList<BinaryNode> children = new ArrayList<>();

		public BinaryNode(int pid){
			this.pid = pid;
		}

		public String toString(){
			return "" + pid;
		}

		public BinaryNode getNthChild(int n){
			return this.children.get(n);
		}

		
		public void setChild(BinaryNode child){
			children.add(child);
		}


		public int get(){
			return this.pid;
		}

		public void set(int pid ){
			this.pid =pid;
		}
		public int getNumOfChildren(){
			return children.size();
		}


	}// end binary node class