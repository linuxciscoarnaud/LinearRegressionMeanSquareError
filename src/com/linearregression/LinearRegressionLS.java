/**
 * 
 */
package com.linearregression;

import java.io.IOException;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @author Arnaud
 *
 */
public class LinearRegressionLS {

	private static double m;
	private static double bias;
	
	// this method takes in the training data and returns an array of two elements which are
	// respectively the means of independent and dependent data
	static double[] getMean_x_y(double[][] train_X) {
		double[] data = new double[2];
		
		double mean_x = 0.0;
		double mean_y = 0.0;
		
		for (int i = 0; i < train_X.length; i++) {
			mean_x += train_X[i][0];
			mean_y += train_X[i][1];
		}
		data[0] = mean_x/train_X.length;
		data[1] = mean_y/train_X.length;
		
		return data;
	}
	
	static double getSlope(double[][] train_X, double[] means_x_y) {
		double[] x_xmean = new double[train_X.length];
		double[] y_ymean = new double[train_X.length];
		double[] productNum = new double[train_X.length];
		double[] productDen = new double[train_X.length];
		double sumNum = 0.0;
		double sumDen = 0.0;
		
		for (int j = 0; j < train_X.length; j++) {
			x_xmean[j] = train_X[j][0] - means_x_y[0];
		}
		
		for (int k = 0; k < train_X.length; k++) {
			y_ymean[k] = train_X[k][1] - means_x_y[1];
		}
		
		for (int i = 0; i < train_X.length; i++) {
			productNum[i] = x_xmean[i] * y_ymean[i];
		}
		
		for (int i = 0; i < train_X.length; i++) {
			productDen[i] = x_xmean[i] * x_xmean[i];
		}
		
		for (int i = 0; i < train_X.length; i++) {
			sumNum += productNum[i];
		}
		
		for (int i = 0; i < train_X.length; i++) {
			sumDen += productDen[i];
		}
		
		return sumNum/sumDen;
	}
	
	static double getBias(double[] means_x_y, double m) {
		return means_x_y[1] - m * means_x_y[0];
	}
	
	static double getRSquare(double[][] train_X, double[] means_x_y, double[] predicted_T) {
		double[] y_ymean = new double[train_X.length];
		double[] yp_ymean = new double[train_X.length];
		double[] productNum = new double[train_X.length];
		double[] productDen = new double[train_X.length];
		double sumNum = 0.0;
		double sumDen = 0.0;
		
		for (int i = 0; i < train_X.length; i++) {
			y_ymean[i] = train_X[i][1] - means_x_y[1];
		}
		
		for (int i = 0; i < train_X.length; i++) {
			yp_ymean[i] = predicted_T[i] - means_x_y[1];
		}
		
		for (int i = 0; i < train_X.length; i++) {
			productNum[i] = yp_ymean[i] * yp_ymean[i];
		}
		
		for (int i = 0; i < train_X.length; i++) {
			productDen[i] = y_ymean[i] * y_ymean[i];
		}
		
		for (int i = 0; i < train_X.length; i++) {
			sumNum += productNum[i];
		}
		
		for (int i = 0; i < train_X.length; i++) {
			sumDen += productDen[i];
		}
		
		return sumNum/sumDen;
	}
	
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws BiffException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public static void main(String[] args) throws BiffException, IOException, RowsExceededException, WriteException {
		// TODO Auto-generated method stub
		DataManager dataManager = new DataManager ();
		Constants constants = new Constants();

		final int train_N = dataManager.countData(constants.getTrainDataFile());   // number of training data
		final int test_N = dataManager.countData(constants.getTestDataFile());    // number of test data
		final int dim = 1;        // dimension of data
		
		double[] mean_x_y = new double[2];  // means of respectively independent and dependent data.
		double[][] train_X = new double[train_N][dim + 1];  // input data for training
		double[][] test_X = new double[test_N][dim + 1];   // input data for testing
		
		double[] predicted_T = new double[train_N];     // output data predicted by the model
		
		double rSquare = 0.0;
		
		// loading training data
		dataManager.loadData(train_X, constants.getTrainDataFile());
		System.out.println("NUMBER TRAINING DATA: " + train_X.length);
		
		// loading test data
		dataManager.loadData(test_X, constants.getTestDataFile());
		System.out.println("NUMBER TEST DATA: " + test_X.length);
		
		// calculate means of independent and dependent data
		mean_x_y = getMean_x_y(train_X);	
		System.out.println("MEAN_X: " + mean_x_y[0]);
		System.out.println("MEAN_Y: " + mean_x_y[1]);
		
		// calculate the slope
		m = getSlope(train_X, mean_x_y);
		System.out.println("SLOPE: " + m);
		
		// calculate the bias
		bias = getBias(mean_x_y, m);
		System.out.println("BIAS: " + bias);
		
		// make the prediction
		for (int i = 0; i < train_X.length; i++) {
			predicted_T[i] = train_X[i][0] * m + bias;
		}
		
		// write predicted values on file
		//dataManager.writePredictions(predicted_T);
		
		// calculate the RSquare
		rSquare = getRSquare(train_X, mean_x_y, predicted_T);
		System.out.println("RSQUARE: " + rSquare);
	}
}
