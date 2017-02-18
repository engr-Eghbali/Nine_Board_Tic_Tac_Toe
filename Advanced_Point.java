/*
 * Author: Zhishen Pan
 * Date: 2017-02-15
 */

public class Advanced_Point {

	int matrix, position;

	public Advanced_Point(int matrix, int position) {
		this.matrix = matrix;
		this.position = position;
	}

	@Override
	public String toString(){
		return "["+(matrix+1)+","+(position+1)+"]";
	}
}
